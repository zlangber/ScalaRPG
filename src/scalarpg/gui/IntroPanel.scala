package scalarpg.gui

import java.awt.{Font, Graphics2D, Dimension, Color}
import swing.Panel

class IntroPanel extends Panel {

  background = Color.black
  preferredSize = new Dimension(512, 512)
  listenTo(keys)

  def drawCenteredString(s: String, g: Graphics2D, xOffset: Int, yOffset: Int) {

    val rect = g.getFontMetrics(g.getFont).getStringBounds(s, g)

    val x = (size.width - rect.getWidth) / 2 + xOffset
    val y = (size.height - rect.getHeight) / 2 + yOffset

    g.drawString(s, x.toFloat, y.toFloat)
  }

  override def paint(g: Graphics2D) {
    super.paint(g)

    g.setColor(Color.white)
    g.setFont(new Font(g.getFont.getFontName, Font.PLAIN, 16))
    drawCenteredString("ScalaRPG", g, 0, 0)
    g.setFont(new Font(g.getFont.getFontName, Font.PLAIN, 12))
    drawCenteredString("Something cool will be here, eventually...", g, 0, 20)
  }
}
