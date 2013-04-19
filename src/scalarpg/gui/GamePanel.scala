package scalarpg.gui

import java.awt.{Graphics2D, Dimension, Color}
import scala.swing.Panel
import scala.swing.event.KeyPressed
import scalarpg.Client

class GamePanel extends Panel {

  preferredSize = new Dimension(512, 512)
  background = Color.white

  listenTo(keys)
  reactions += {
    case e: KeyPressed => if (Client.rmiServer != null) Client.rmiServer.handleEvent(Client.rmiClient, e.key)
  }

  override def paint(g: Graphics2D) {
    super.paint(g)
    Client.rmiClient.render(g)
  }
}