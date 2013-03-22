package scalarpg.net.client

import scalarpg.entity.Player
import scalarpg.eventbus.event.NetworkEvent
import scalarpg.world.World
import scalarpg.net.server.RMIServer

@remote
trait RMIClient {

  val server: RMIServer
  val player: Player

  def connect()
  def disconnect()
  def handleEvent(e: NetworkEvent[Any])
  def world:World
}
