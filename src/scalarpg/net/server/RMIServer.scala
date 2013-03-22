package scalarpg.net.server

import scalarpg.eventbus.event.NetworkEvent
import scalarpg.net.client.RMIClient
import scalarpg.world.World

@remote
trait RMIServer {

  val world: World

  def connect(client: RMIClient):Boolean
  def disconnect(client: RMIClient)
  def playerList:Seq[String]
  def handleEvent(e: Object)
  def sendEvent(e: NetworkEvent[Any], source: RMIClient)
  def sendEventToAll(e: NetworkEvent[Any])
}