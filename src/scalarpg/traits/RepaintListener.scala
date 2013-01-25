package scalarpg.traits

import eventbus.EventHandler
import scalarpg.eventbus.event.RepaintEvent

trait RepaintListener {

  @EventHandler
  def paint(event: RepaintEvent)
}
