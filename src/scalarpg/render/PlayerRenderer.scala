package scalarpg.render

import scalarpg.entity.{Player, Entity}
import java.awt.Graphics2D

class PlayerRenderer(player: Player) extends EntityRenderer(player) {
  override def render(g: Graphics2D) {
    super.render(g)
    g.drawString(player.username, player.position.x, player.position.y - 4)
  }
}
