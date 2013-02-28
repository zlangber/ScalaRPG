package scalarpg.net.server

import collection.mutable
import java.io.File
import java.rmi.server.UnicastRemoteObject
import scala.xml.XML
import scalarpg.eventbus.event.Event
import scalarpg.net.client.RMIClient
import scalarpg.world.World

class RMIServerImpl extends UnicastRemoteObject with RMIServer {

  val clients = mutable.Buffer[RMIClient]()

  val world = new World()

  def connect(client: RMIClient): Boolean = {

    if (clients.filter(_.player.username == client.player.username).length > 0) {
      println("Rejected connection from " + client)
      return false
    }

    println("Accepted connection from " + client.player.username)
    clients += client
    world.players += client.player
    true
  }

  def disconnect(client: RMIClient) {

    if (!clients.contains(client)) return

    println(client.player.username + " disconnected")
    clients -= client
  }

  def sendEvent(client: RMIClient, e: Event[Any]) {
    clients.view.filter(_ != client).foreach(_.handleEvent(e))
  }


  def playerList: Seq[String] = {
    clients.map(_.player.username)
  }

  def worldData: xml.Node = {
    val file = new File("resources/levels/level0.xml")
    XML.loadFile(file)
  }
}