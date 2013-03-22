package scalarpg.gui

import java.awt.{Graphics2D, Dimension, Color}
import scala.swing.Panel
import scala.swing.event.{KeyReleased, KeyPressed}
import scalarpg.eventbus.event.{KeyEvent, RepaintEvent, TickEvent}
import scalarpg.eventbus.{EventHandler, EventBusService}
import scalarpg.Client

class GamePanel extends Panel {

  EventBusService.subscribe(this)

  preferredSize = new Dimension(512, 512)
  background = Color.white

  listenTo(keys)
  reactions += {
    case e:KeyPressed => Client.rmiClient.server.handleEvent(new KeyEvent(Client.rmiClient, e.key, e.modifiers))
  }

  @EventHandler
  def tick(event: TickEvent) {
    repaint()
  }

  override def paint(g: Graphics2D) {
    super.paint(g)
    EventBusService.publish((new RepaintEvent(this, g)))
  }
}