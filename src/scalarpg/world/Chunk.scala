package scalarpg.world

import scala.collection.mutable
import scalarpg.entity.Entity

class Chunk(val index: Int) extends Serializable {

  lazy val tiles = Array.tabulate(16, 16)((x, y) => new Tile(this, x * 32, y * 32, defaultTexture))
  var entities = mutable.Buffer[Entity]()

  val spriteKey = "world"
  var defaultTexture = 0

  def getTile(x: Int, y: Int): Tile = {
    tiles(x)(y)
  }

  def getTileAt(x: Int, y: Int): Tile = {
    getTile(x / 32, y / 32)
  }

  def tick() {}
}
