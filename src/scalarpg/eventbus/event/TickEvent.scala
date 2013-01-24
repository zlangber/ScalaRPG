package scalarpg.eventbus.event

class TickEvent extends Event

object TickEvent {
  def apply():TickEvent = new TickEvent
}
