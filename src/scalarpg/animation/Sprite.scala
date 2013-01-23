package scalarpg.animation

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Sprite(name: String) {

  var file:File = null
  var sheet:BufferedImage = null
  var images:Array[BufferedImage] = null

  load(name)

  def apply(index: Int):BufferedImage = {
    images(index)
  }

  def load(name: String) {

    file = new File("resources/sprites/" + name)
    sheet = ImageIO.read(file)
    images = new Array[BufferedImage](64)

    for (i <- 0 until 64) {
      images(i) = sheet.getSubimage((i % 8) * 32, (i / 8) * 32, 32, 32)
    }
  }
}
