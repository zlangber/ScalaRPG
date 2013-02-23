package scalarpg.client

import scalarpg.eventbus.event.Event

@remote
trait RMIClient {

  val username: String

  def connect()
  def disconnect()
  def handleEvent(e: Event[Any])
}
