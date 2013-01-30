package scalarpg.world

import eventbus.EventHandler
import scalarpg.traits.{RepaintListener, TickListener}
import scalarpg.eventbus.event.{RepaintEvent, TickEvent}
import scalarpg.animation.SpriteCache

class Chunk(world: World, val index:Int) extends TickListener with RepaintListener {

  private lazy val tiles = Array.tabulate(16, 16)((x, y) => new Tile(this, x * 32, y * 32, defaultTexture))

  val sprite = SpriteCache("world")
  var defaultTexture = 0

  def getTile(x: Int, y: Int):Tile = {
    tiles(x)(y)
  }

  def getTileAt(x: Int, y: Int):Tile = {
    getTile(x / 32, y / 32)
  }

  @EventHandler
  def tick(event: TickEvent) {

    if (world.activeChunk != this) return
  }

  @EventHandler
  def paint(event: RepaintEvent) {

    if (world.activeChunk != this) return

    tiles.flatten.foreach(_.paint(event.graphics))
  }
}
