package scalarpg.gui

import swing.Panel
import java.awt.{Graphics2D, Dimension, Color}
import swing.event.KeyPressed
import scalarpg.ScalaRPG

class IntroPanel extends Panel {

  background = Color.black
  preferredSize = new Dimension(800, 800)
  listenTo(keys)
  reactions += {
    case e:KeyPressed => ScalaRPG.start()
  }

  override protected def paintComponent(g: Graphics2D) {
    super.paintComponent(g)
  }
}
