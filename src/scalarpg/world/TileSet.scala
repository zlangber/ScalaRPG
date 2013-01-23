package scalarpg.world

import java.awt.{Graphics2D, Point}
import scalarpg.traits.Paintable

class TileSet(size: Int) extends Paintable {

  val tiles = Array.tabulate(size, size)((x, y) => new Tile(x * 32, y * 32))

  def apply(x: Int, y: Int):Tile = {
    tiles(x)(y)
  }

  def getSubsetAround(tilePos: Point, size: Int):TileSet = {

    val subset = new TileSet(size: Int)

    var startX = 0
    var startY = 0

    if (tilePos.x > size) startX = tilePos.x
    if (tilePos.y > size) startY = tilePos.y

    var i, j = 0
    for (x <- startX until startX + size) {
      for (y <- startY until startY + size) {
        subset.tiles(i)(j) = tiles(x)(y)
        i += 1
      }
      i = 0
      j += 1
    }

    subset
  }

  def paint(g: Graphics2D) {
    tiles.flatten.foreach(_.paint(g))
  }
}
