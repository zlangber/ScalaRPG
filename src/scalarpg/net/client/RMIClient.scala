package scalarpg.net.client

import scalarpg.eventbus.event.Event
import scalarpg.entity.Player

@remote
trait RMIClient {

  val player: Player

  def connect()
  def disconnect()
  def handleEvent(e: Event[Any])
}
