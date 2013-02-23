package scalarpg.gui

import swing._
import event.{KeyReleased, KeyPressed}
import java.awt.Color
import scalarpg.eventbus.event.{RepaintEvent, TickEvent}
import scalarpg.eventbus.{EventHandler, EventBusService}

class GamePanel extends Panel {

  EventBusService.subscribe(this)

  preferredSize = new Dimension(512, 512)
  background = Color.white

  listenTo(keys)
  reactions += {
    case e:KeyPressed => EventBusService.publish(e)
    case e:KeyReleased => EventBusService.publish(e)
  }

  @EventHandler
  def tick(event: TickEvent) {
    repaint()
  }

  override def paint(g: Graphics2D) {
    super.paint(g)
    EventBusService.publish((RepaintEvent(this, g)))
  }
}
