package scalarpg.traits

import eventbus.EventHandler
import scalarpg.eventbus.event.TickEvent

trait TickListener {

  @EventHandler
  def tick(event: TickEvent)
}
