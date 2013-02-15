package scalarpg.animation

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Sprite(name: String) extends Serializable {

  var file = new File("resources/sprites/" + name)
  var sheet = ImageIO.read(file)
  var images = for (i <- 0 until 64) yield sheet.getSubimage((i % 8) * 32, (i / 8) * 32, 32, 32)

  def apply(index: Int):BufferedImage = {
    images(index)
  }

  override def toString():String = {
    name
  }
}