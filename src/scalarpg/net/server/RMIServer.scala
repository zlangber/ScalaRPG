package scalarpg.server

import scalarpg.client.RMIClient
import scalarpg.eventbus.event.Event
import scalarpg.world.World

@remote
trait RMIServer {

  def connect(client: RMIClient):Boolean
  def disconnect(client: RMIClient)
  def sendEvent(client: RMIClient, event: Event[Any])
  def getPlayerList():Seq[String]
  def getWorld():World
}