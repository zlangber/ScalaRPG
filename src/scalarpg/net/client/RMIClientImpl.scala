package scalarpg.net.client

import java.rmi.server.UnicastRemoteObject
import scalarpg.Client
import scalarpg.entity.Player
import scalarpg.eventbus.event.{RepaintEvent, PlayerConnectedEvent, PlayerDisconnectedEvent, NetworkEvent}
import scalarpg.eventbus.{EventHandler, EventBusService}
import scalarpg.net.server.RMIServer
import scalarpg.world.WorldClient

class RMIClientImpl(val player: Player, override val server: RMIServer) extends UnicastRemoteObject with RMIClient {

  var world: WorldClient = null

  EventBusService.subscribe(this)
  EventBusService.subscribe(ClientEventHandler)

  @EventHandler
  def repaint(e: RepaintEvent) {
    world.repaint(e)
  }

  def connect() {

    val accepted = server.connect(this)
    if (!accepted) {
      println("Rejected by server")
      return
    }

    println("Connected to server.")
    println("Loading world...")

    world = new WorldClient(server.world.chunks, 0)
    Client.start()
  }

  def disconnect() {
    server.disconnect(this)
    println("Disconnected")
    Client.showTitleScreen()
  }

  def handleEvent(e: NetworkEvent[Any]) {
    EventBusService.publish(e)
  }
}