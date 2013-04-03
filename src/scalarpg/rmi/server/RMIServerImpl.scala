package scalarpg.rmi.server

import java.rmi.server.UnicastRemoteObject
import scala.collection.mutable
import scalarpg.render.{RenderStateImpl, RenderState}
import scalarpg.rmi.client.RMIClient
import scalarpg.world.World
import scalarpg.entity.Player
import scala.swing.event.KeyPressed

class RMIServerImpl extends UnicastRemoteObject with RMIServer {

  val clients = mutable.Buffer[RMIClient]()

  val world = new World()
  world.load("level0")

  def connect(client: RMIClient): (Boolean, String) = {

    if (clients.length >= 8) {
      println("Rejected " + client.username + ", server full.")
      return false -> "Server full"
    }

    if (clients.map(_.username).contains(client.username)) {
      println("Rejected " + client.username + ", username in use.")
      return false -> "Username in use"
    }

    clients += client
    world.addEntity(new Player(client.username), 0)
    println("Accepted connection from " + client.username)
    true -> "Connection accepted"
  }

  def disconnect(client: RMIClient) {
    clients -= client
    println(client.username + " disconnected")
  }

  def playerList: Seq[String] = {
    clients.map(_.username).toSeq
  }

  def getRenderState(client: RMIClient): RenderState = {
    world.getRenderStateFor(client.username)
  }

  def handleEvent(client: RMIClient, e: Object) {
    e match {
      case kpe: KeyPressed =>
    }
  }
}
