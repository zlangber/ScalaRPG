package scalarpg.rmi.server

import scalarpg.rmi.client.RMIClient
import scalarpg.render.RenderState

@remote
trait RMIServer {

  def connect(client: RMIClient):(Boolean, String)
  def disconnect(client: RMIClient)
  def playerList:Seq[String]
  def handleEvent(client: RMIClient, e: Object)
  def getRenderState(client: RMIClient):RenderState
}