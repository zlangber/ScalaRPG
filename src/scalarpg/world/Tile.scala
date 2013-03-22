package scalarpg.world

import collection.mutable
import java.awt.Graphics2D
import scalarpg.animation.SpriteCache

class Tile(val chunk: Chunk, val x: Int, val y: Int, var baseTextureIndex: Int) extends Serializable {

  var layers = mutable.Buffer[Layer]()

  var solid = false

  def paint(g: Graphics2D) {
    g.drawImage(SpriteCache(chunk.spriteKey)(baseTextureIndex), x, y, null)
    layers.foreach(l => g.drawImage(SpriteCache(l.sheet)(l.texture), x, y, null))
  }
}
