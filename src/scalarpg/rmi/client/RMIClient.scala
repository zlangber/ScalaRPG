package scalarpg.rmi.client

import scalarpg.render.RenderState
import scalarpg.rmi.server.RMIServer

@remote
trait RMIClient {

  var username: String

  def updateRenderState(renderState: RenderState)
}
