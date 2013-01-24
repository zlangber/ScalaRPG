package scalarpg.traits

import eventbus.EventHandler
import scalarpg.eventbus.event.RepaintEvent

trait PaintListener {

  @EventHandler
  def paint(event: RepaintEvent)
}
