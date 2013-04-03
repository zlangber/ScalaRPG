package scalarpg.render

import java.awt.Graphics2D
import scala.collection.mutable

trait RenderState extends Serializable {

  protected var renderers: mutable.PriorityQueue[Renderer]

  def +=(renderer: Renderer)

  def render(g: Graphics2D) {
    renderers.foreach(_.render(g))
  }

  def tick() {
    renderers.foreach(_.tick())
  }
}