package scalarpg.world

import scalarpg.animation.SpriteCache
import scalarpg.entity.Player
import scalarpg.eventbus.event.PlayerLeftChunkEvent
import scalarpg.eventbus.{EventHandler, EventBusService}
import scalarpg.util.Direction

class WorldOld extends Serializable {

  val chunks = Array.fill(2)(new Array[Chunk](2))
  var activeChunk: Chunk = null

  val player = new Player(this, "")

  EventBusService.subscribe(this)

  @EventHandler
  def playerLeftChunk(event: PlayerLeftChunkEvent) {
    val chunk = getChunkTowards(event.direction)
    if (chunk.isDefined) {
      event.source.repositionPlayer(event.direction)
      activeChunk = chunk.get
    }
  }

  def getChunkTowards(direction: Direction.Value): Option[Chunk] = {

    var row = activeChunk.index / chunks.length
    var column = activeChunk.index % chunks.length

    direction match {
      case Direction.Down => row += 1
      case Direction.Left => column -= 1
      case Direction.Right => column += 1
      case Direction.Up => row -= 1
    }

    if (row >= 0 && row < chunks.length && column >= 0 && column < chunks.length)
      Option(chunks(row)(column))

    else Option.empty
  }

  def load(worldData: xml.Node) {

    (worldData \ "chunk").toArray.zipWithIndex.foreach(data => {

      val chunkXML = data._1
      val index = data._2

      val chunk = new Chunk(this, index)
      chunk.defaultTexture = (chunkXML \ "defaults" \ "@texture").text.toInt

      (chunkXML \ "tiles" \ "tile").foreach(tileXML => {

        val x = (tileXML \ "@x").text.toInt
        val y = (tileXML \ "@y").text.toInt
        val tile = chunk.getTile(x, y)

        (tileXML \ "layer").foreach(layerXML => {

          val sheet = (layerXML \ "@sheet").text
          val layerTexture = (layerXML \ "@texture").text.toInt
          val solid = (layerXML \ "@solid").text

          tile.layers += SpriteCache(sheet)(layerTexture)
          tile.solid = if (!solid.isEmpty) solid.toBoolean else false
        })
      })

      val row = index / chunks.length
      val column = index % chunks.length
      chunks(row)(column) = chunk
    })

    activeChunk = chunks(0)(0)

    chunks.flatten.foreach(EventBusService.subscribe(_))
    EventBusService.subscribe(player)
  }
}
