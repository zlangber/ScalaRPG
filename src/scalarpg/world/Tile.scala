package scalarpg.world

import scalarpg.traits.Paintable
import java.awt.{Point, Graphics2D}
import scalarpg.animation.SpriteCache
import java.awt.image.AffineTransformOp
import java.awt.geom.AffineTransform
import util.Random

class Tile(x: Int, y: Int) extends Paintable {

  val pos = new Point(x, y)
  val texture = SpriteCache("world.png")(0)

  val rotations = Random.shuffle(List(0, 90, 180, 270))
  val texture2 = new AffineTransformOp(
    AffineTransform.getRotateInstance(math.toRadians(rotations(0)), texture.getWidth / 2, texture.getHeight / 2),
    AffineTransformOp.TYPE_BILINEAR
  ).filter(texture, null)

  def paint(g: Graphics2D) {
    g.drawImage(texture2, pos.x, pos.y, null)
  }
}