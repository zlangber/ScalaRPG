package scalarpg.world

import scalarpg.entity.Player
import java.io.File
import xml.XML
import scalarpg.eventbus.EventBusService
import scalarpg.animation.SpriteCache
import scalarpg.util.Direction

class World {

  val chunks = Array.fill(2)(new Array[Chunk](2))
  var activeChunk:Chunk = null

  val player = new Player(this)

  def getChunkTowards(direction: Direction.Value):Option[Chunk] = {

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

  def load(filename: String) {

    val file = new File(s"resources/levels/$filename")
    val xml = XML.loadFile(file)

    (xml \ "chunk").toArray.zipWithIndex.foreach( data => {

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
          val isSolid = (layerXML \ "@solid").text.toBoolean

          tile.layers += SpriteCache(sheet)(layerTexture)
          tile.solid = isSolid
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
