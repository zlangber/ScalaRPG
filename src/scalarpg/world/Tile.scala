package scalarpg.world

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import collection.mutable

class Tile(val chunk: Chunk, val x: Int, val y: Int, var baseTextureIndex: Int) {

  var layers = mutable.Buffer[BufferedImage]()

  var solid = false

  def paint(g: Graphics2D) {
    g.drawImage(chunk.sprite(baseTextureIndex), x, y, null)
    layers.foreach(g.drawImage(_, x, y, null))
  }
}
