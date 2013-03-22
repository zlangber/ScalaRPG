package scalarpg.net.server

import collection.mutable
import java.rmi.server.UnicastRemoteObject
import scalarpg.eventbus.event.{PlayerConnectedEvent, PlayerDisconnectedEvent, NetworkEvent}
import scalarpg.net.client.RMIClient
import scalarpg.world.World
import scalarpg.eventbus.EventBusService

class RMIServerImpl extends UnicastRemoteObject with RMIServer {

  val clients = mutable.Buffer[RMIClient]()

  val world = new World()
  world.load("level0")

  ServerEventHandler.server = this
  EventBusService.subscribe(ServerEventHandler)

  def connect(client: RMIClient): Boolean = {

    if (clients.filter(_.player.username == client.player.username).length > 0) {
      println("Rejected connection from " + client)
      return false
    }

    println("Accepted connection from " + client.player.username)
    clients += client
    val chunkIndex = 0
    world.addEntity(client.player, chunkIndex)
    sendEvent(new PlayerConnectedEvent(client.player, chunkIndex), client)
    true
  }

  def disconnect(client: RMIClient) {

    if (!clients.contains(client)) return

    println(client.player.username + " disconnected")
    clients -= client
    world.removeEntity(client.player)
    sendEvent(new PlayerDisconnectedEvent(client.player), client)
  }

  def playerList: Seq[String] = {
    clients.map(_.player.username)
  }

  def handleEvent(e: Object) {
    EventBusService.publish(e)
  }

  def sendEvent(e: NetworkEvent[Any], source: RMIClient) {
    clients.filter(_ != source).foreach(_.handleEvent(e))
  }

  def sendEventToAll(e: NetworkEvent[Any]) {
    clients.foreach(_.handleEvent(e))
  }
}