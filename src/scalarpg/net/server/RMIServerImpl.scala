package scalarpg.server

import java.rmi.server.UnicastRemoteObject
import scalarpg.client.RMIClient
import collection.mutable
import scalarpg.eventbus.event.Event
import scalarpg.world.World

class RMIServerImpl extends UnicastRemoteObject with RMIServer {

  val clients = mutable.Buffer[RMIClient]()

  val world = new World()

  def connect(client: RMIClient):Boolean = {

    if (clients.filter(_.username == client.username).length > 0) {
      println("Rejected connection from " + client)
      return false
    }

    println("Accepted connection from " + client.username)
    clients += client
    true
  }

  def disconnect(client: RMIClient) {

    if (!clients.contains(client)) return

    println("Client " + client.username + " disconnected")
    clients -= client
  }

  def sendEvent(client: RMIClient, e: Event[Any]) {
    clients.filter(_ != client).foreach(_.handleEvent(e))
  }


  def getPlayerList(): Seq[String] = {
    clients.map(_.username)
  }

  def getWorld():World = {
    new World
  }
}