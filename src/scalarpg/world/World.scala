package scalarpg.world

import scalarpg.entity.Player
import java.io.File
import xml.XML
import scalarpg.eventbus.EventBusService

class World {

  val chunks = new Array[Chunk](4)
  val currentChunkIndex = 0

  val player = new Player(this)

  def load(filename: String) {

    val file = new File(s"resources/levels/$filename")
    val xml = XML.loadFile(file)

    (xml \ "chunk").toArray.zipWithIndex.foreach( data => {

      chunks(data._2) = new Chunk(this, data._2)
      chunks(data._2).defaultTexture = (data._1 \ "defaults" \ "@texture").text.toInt
    })

    chunks.foreach(EventBusService.subscribe(_))
    EventBusService.subscribe(player)
  }
}
