package scalarpg.world

import java.util.UUID
import scala.collection.mutable
import scala.xml.XML
import scalarpg.entity.{Player, Entity}
import scalarpg.render._

class World extends Serializable {

  val chunks = new Array[Chunk](4)
  val entityChunkMap = new mutable.HashMap[UUID, Int]()

  def getEntity(id: UUID):Entity = {

    var count = 0
    var entity: Entity = null
    chunks.foreach(chunk => {
      chunk.entities.foreach(e => {
        if (e.id == id)
          entity = e
      })
    })
    entity
  }

  def addEntity(entity: Entity, chunkIndex: Int) {
    entityChunkMap += entity.id -> chunkIndex
    chunks(chunkIndex).entities += entity
  }

  def removeEntity(entity: Entity) {
    chunks(entityChunkMap(entity.id)).entities -= entity
  }

  def getRenderStateFor(username: String): RenderState = {

    val chunk = chunks(0)

    val renderState = new RenderStateImpl()
    renderState += new ChunkRenderer(chunk)
    chunk.entities.foreach( _ match {
      case p: Player => renderState += new PlayerRenderer(p)
      case e: Entity => renderState += new EntityRenderer(e)
    })
    renderState
  }

  def load(level: String) {

    val worldData = XML.loadFile("resources/levels/" + level + ".xml")

    (worldData \ "chunk").toArray.zipWithIndex.foreach(data => {

      val chunkXML = data._1
      val index = data._2

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

      chunks(index) = chunk
    })
  }
}
