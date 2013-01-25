package scalarpg.world

import eventbus.EventHandler
import scalarpg.traits.{RepaintListener, TickListener}
import scalarpg.eventbus.event.{RepaintEvent, TickEvent}
import scalarpg.animation.SpriteCache

class Chunk(defaultTexture: Int) extends TickListener with RepaintListener {

  val sprite = SpriteCache("world")

  @EventHandler
  def tick(event: TickEvent) {}

  @EventHandler
  def paint(event: RepaintEvent) {

    for (i <- 0 until 25; j <- 0 until 25)
      event.graphics.drawImage(sprite(defaultTexture), i * 32, j * 32, null)
  }
}
