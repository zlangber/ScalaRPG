package scalarpg.world

import java.util.UUID
import scala.collection.mutable
import scala.xml.XML
import scalarpg.entity.{Player, Entity}
import scalarpg.render._
import scalarpg.util.Direction
import scalarpg.rmi.server.RMIServerImpl

class World(server: RMIServerImpl) extends Serializable {

  val chunks = Array.fill(2)(new Array[Chunk](2))
  val entityChunkMap = new mutable.HashMap[UUID, Int]()

  def getPlayer(username: String): Player = {
    var player: Player = null
    chunks.flatten.foreach(chunk => {
      chunk.entities.foreach(e => {
        e match {
          case p: Player => if (p.username == username) player = p
          case _ =>
        }
      })
    })
    player
  }

  def getEntity(id: UUID): Entity = {

    var entity: Entity = null
    chunks.flatten.foreach(chunk => {
      chunk.entities.foreach(e => {
        if (e.id == id)
          entity = e
      })
    })
    entity
  }

  def addEntity(entity: Entity, chunkIndex: Int) {

    entityChunkMap += entity.id -> chunkIndex
    getChunkFromIndex(chunkIndex).entities += entity
  }

  def removeEntity(entity: Entity) {

    val chunkIndex = entityChunkMap(entity.id)
    getChunkFromIndex(chunkIndex).entities -= entity
    entityChunkMap -= entity.id
  }

  def moveEntity(entity: Entity, direction: Direction.Value) {

    val (row, column) = getChunkCoordsFromIndex(entityChunkMap(entity.id))
    var newRow, newColumn = -1
    direction match {
      case Direction.Up => {
        if (row - 1 > -1) {
          newRow = row - 1
          newColumn = column
        }
      }
      case Direction.Down => {
        if (row + 1 < chunks.length) {
          newRow = row + 1
          newColumn = column
        }
      }
      case Direction.Left => {
        if (column - 1 > -1) {
          newRow = row
          newColumn = column - 1
        }
      }
      case Direction.Right => {
        if (column + 1 < chunks(row).length) {
          newRow = row
          newColumn = column + 1
        }
      }
    }

    if (newRow > -1 && newColumn > -1) {
      removeEntity(entity)
      addEntity(entity, newRow * 2 + newColumn)
      entity.reposition(direction)
    }
  }

  def getRenderStateFor(username: String): RenderState = {

    val player = getPlayer(username)
    val (row, column) = getChunkCoordsFromIndex(entityChunkMap(player.id))
    val chunk = chunks(row)(column)

    val renderState = new RenderStateImpl()
    renderState += new ChunkRenderer(chunk)
    chunk.entities.foreach(_ match {
      case p: Player => renderState += new PlayerRenderer(p)
      case e: Entity => renderState += new EntityRenderer(e)
    })
    renderState
  }

  def forceRenderUpdate() {
    server.needsRenderUpdate = true
  }

  def tick() {
    chunks.flatten.foreach(_.tick())
  }

  def getChunkCoordsFromIndex(index: Int): (Int, Int) = index / 2 -> index % 2

  def getChunkFromIndex(index: Int): Chunk = {
    val (row, column) = getChunkCoordsFromIndex(index)
    chunks(row)(column)
  }

  def load(level: String) {

    val worldData = XML.loadFile("resources/levels/" + level + ".xml")

    (worldData \ "chunk").toArray.zipWithIndex.foreach(data => {

      val (chunkXML, index) = data

      val chunk = new Chunk(index)
      chunk.defaultTexture = (chunkXML \ "defaults" \ "@texture").text.toInt

      (chunkXML \ "tiles" \ "tile").foreach(tileXML => {

        val x = (tileXML \ "@x").text.toInt
        val y = (tileXML \ "@y").text.toInt
        val tile = chunk.getTile(x, y)

        (tileXML \ "layer").foreach(layerXML => {

          val sheet = (layerXML \ "@sheet").text
          val layerTexture = (layerXML \ "@texture").text.toInt
          val solid = (layerXML \ "@solid").text

          tile.layers += Layer(sheet, layerTexture)
          tile.solid = if (!solid.isEmpty) solid.toBoolean else false
        })
      })

      val (row, column) = getChunkCoordsFromIndex(index)
      chunks(row)(column) = chunk
    })
  }
}
