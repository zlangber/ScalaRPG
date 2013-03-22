package scalarpg.world

import scala.collection.mutable
import scalarpg.entity.Entity
import scalarpg.eventbus.EventHandler
import scalarpg.eventbus.event.{RepaintEvent, TickEvent}

class Chunk(val index: Int) extends Serializable {

  private lazy val tiles = Array.tabulate(16, 16)((x, y) => new Tile(this, x * 32, y * 32, defaultTexture))
  var entities = mutable.Buffer[Entity]()

  val spriteKey = "world"
  var defaultTexture = 0

  def getTile(x: Int, y: Int): Tile = {
    tiles(x)(y)
  }

  def getTileAt(x: Int, y: Int): Tile = {
    getTile(x / 32, y / 32)
  }

  @EventHandler
  def tick(event: TickEvent) {

  }

  @EventHandler
  def paint(e: RepaintEvent) {
    tiles.flatten.foreach(_.paint(e.graphics))
    entities.foreach(_.paint(e))
  }
}
