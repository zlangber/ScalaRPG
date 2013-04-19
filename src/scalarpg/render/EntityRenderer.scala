package scalarpg.render

import java.awt.Graphics2D
import scalarpg.entity.Entity

class EntityRenderer(entity: Entity) extends Renderer {

  def tick() {
    entity.tick()
  }

  def render(g: Graphics2D) {
    g.drawImage(entity.animationState.currentFrame, entity.position.x, entity.position.y, null)
  }
}
