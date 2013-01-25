package scalarpg.gui

import eventbus.EventHandler
import swing._
import event.{KeyReleased, KeyPressed}
import java.awt.Color
import scalarpg.util.KeyHandler
import scalarpg.eventbus.event.{RepaintEvent, TickEvent}
import scalarpg.eventbus.EventBusService
import scalarpg.traits.TickListener

class GamePanel extends Panel with TickListener {

  EventBusService.subscribe(this)

  preferredSize = new Dimension(800, 800)
  background = Color.white

  listenTo(keys)
  reactions += {
    case e:KeyPressed => KeyHandler.onKeyPressed(e)
    case e:KeyReleased => KeyHandler.onKeyReleased(e)
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
