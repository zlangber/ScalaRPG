package scalarpg.eventbus.event

import swing.Panel
import java.awt.Graphics2D

class RepaintEvent(source: Panel, val graphics: Graphics2D) extends Event[Panel](source)

object RepaintEvent {
  def apply(source: Panel, graphics: Graphics2D):RepaintEvent = new RepaintEvent(source, graphics)
}
