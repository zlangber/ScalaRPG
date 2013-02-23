package scalarpg.client

import java.rmi.server.UnicastRemoteObject
import scalarpg.Client
import scalarpg.eventbus.event.Event
import scalarpg.server.RMIServer

class RMIClientImpl(val username: String, server: RMIServer) extends UnicastRemoteObject with RMIClient {

  def connect() {

    val accepted = server.connect(this)
    if (!accepted) {
      println("Rejected by server")
      sys.exit()
    }

    println("Connected to server.")
    println("Loading world...")

    Client.start()
  }

  def disconnect() {
    server.disconnect(this)
    Client.showTitleScreen()
  }

  def handleEvent(e: Event[Any]) {

  }
}
