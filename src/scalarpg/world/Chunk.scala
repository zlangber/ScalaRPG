package scalarpg.world

import eventbus.EventHandler
import scalarpg.traits.{RepaintListener, TickListener}
import scalarpg.eventbus.event.{RepaintEvent, TickEvent}
import scalarpg.animation.SpriteCache

class Chunk(world: World, index:Int) extends TickListener with RepaintListener {

  val sprite = SpriteCache("world")
  var defaultTexture = 0

  @EventHandler
  def tick(event: TickEvent) {

    if (world.currentChunkIndex != index) return
  }

  @EventHandler
  def paint(event: RepaintEvent) {

    if (world.currentChunkIndex != index) return

    for (i <- 0 until 25; j <- 0 until 25)
      event.graphics.drawImage(sprite(defaultTexture), i * 32, j * 32, null)
  }
}
