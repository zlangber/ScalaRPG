package scalarpg.gui

import swing._
import event.{KeyReleased, KeyPressed}
import java.awt.Color
import scalarpg.util.KeyHandler
import scalarpg.traits.RepaintListener

class GamePanel(repaintListener: RepaintListener) extends Panel {

  preferredSize = new Dimension(800, 800)
  background = Color.white

  listenTo(keys)
  reactions += {
    case e:KeyPressed => KeyHandler.onKeyPressed(e)
    case e:KeyReleased => KeyHandler.onKeyReleased(e)
  }

  override protected def paintComponent(g: Graphics2D) {
    super.paintComponent(g)
    repaintListener.onRepaint(g)
  }
}
