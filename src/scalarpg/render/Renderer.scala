package scalarpg.render

import java.awt.Graphics2D

trait Renderer extends Serializable {

  def tick()
  def render(g: Graphics2D)
}