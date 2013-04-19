package scalarpg.rmi.client

import java.rmi.server.UnicastRemoteObject
import scalarpg.render.RenderState
import java.awt.Graphics2D

class RMIClientImpl(val username: String) extends UnicastRemoteObject with RMIClient {

  var renderState: RenderState = null

  def updateRenderState(renderState: RenderState) = this.renderState = renderState

  def render(g: Graphics2D) = renderState.render(g)
  def tick() = renderState.tick()
}
