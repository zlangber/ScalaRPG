package scalarpg.net.server

import scalarpg.eventbus.event.Event
import scalarpg.net.client.RMIClient

@remote
trait RMIServer {

  def connect(client: RMIClient):Boolean
  def disconnect(client: RMIClient)
  def sendEvent(client: RMIClient, event: Event[Any])
  def playerList:Seq[String]
  def worldData:xml.Node
}