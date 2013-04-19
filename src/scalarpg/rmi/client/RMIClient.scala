package scalarpg.rmi.client

import java.awt.Graphics2D
import scalarpg.render.RenderState

@remote
trait RMIClient {

  val username: String

  def render(g: Graphics2D)
  def tick()
  def updateRenderState(renderState: RenderState)
}
