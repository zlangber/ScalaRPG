package scalarpg.world

import java.awt.{Point, Graphics2D}
import scalarpg.entity.Player
import scalarpg.traits.{Paintable, Tickable}

class World extends Tickable with Paintable {

  val SIZE = 100
  val VIEW_SIZE = 24

  val tileSet = new TileSet(SIZE)
  var view = tileSet.getSubsetAround(new Point(0, 0), VIEW_SIZE)

  val player = new Player(this)

  def updateView() {
    view = tileSet.getSubsetAround(player.getTilePosition(), VIEW_SIZE)
  }

  def tick() {
    player.tick()
  }

  def paint(g: Graphics2D) {

    view.paint(g)
    player.paint(g)
  }
}
