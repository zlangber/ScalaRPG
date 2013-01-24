package scalarpg.gui

import swing.Panel
import java.awt.{Dimension, Color}
import swing.event.KeyPressed
import scalarpg.ScalaRPG

class IntroPanel extends Panel {

  background = Color.black
  preferredSize = new Dimension(800, 800)
  listenTo(keys)
  reactions += {
    case e:KeyPressed => ScalaRPG.start()
  }
}
