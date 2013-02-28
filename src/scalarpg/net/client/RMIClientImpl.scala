package scalarpg.net.client

import java.rmi.server.UnicastRemoteObject
import scalarpg.Client
import scalarpg.entity.Player
import scalarpg.eventbus.event.Event
import scalarpg.net.server.RMIServer

class RMIClientImpl(val player: Player, server: RMIServer) extends UnicastRemoteObject with RMIClient {

  def connect() {

    val accepted = server.connect(this)
    if (!accepted) {
      println("Rejected by server")
      sys.exit()
    }

    println("Connected to server.")
    println("Loading world...")

    val worldData = server.worldData
    Client.start(worldData)
  }

  def disconnect() {
    server.disconnect(this)
    Client.showTitleScreen()
  }

  def handleEvent(e: Event[Any]) {

  }
}
