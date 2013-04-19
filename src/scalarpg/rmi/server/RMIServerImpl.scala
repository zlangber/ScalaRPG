package scalarpg.rmi.server

import java.rmi.server.UnicastRemoteObject
import javax.swing.Timer
import scala.collection.mutable
import scala.swing.Swing
import scala.swing.event.Key
import scalarpg.entity.{Mob, Player}
import scalarpg.render.RenderState
import scalarpg.rmi.client.RMIClient
import scalarpg.util.Direction
import scalarpg.world.World
import scala.util.Random

class RMIServerImpl extends UnicastRemoteObject with RMIServer {

  val clients = mutable.Buffer[RMIClient]()

  val world = new World(this)
  world.load("level0")

  var needsRenderUpdate = false

  //Spawn somne mobs here for now
  for (i <- 0 until 15) {
    val chunkIndex = Random.nextInt(4)
    val mob = new Mob("blueSlime", world)
    world.addEntity(mob, chunkIndex)
  }

  val timer = new Timer(50, Swing.ActionListener(e => {
    world.tick()
    if (needsRenderUpdate) updateRenderStates()
  }))
  timer.start()

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
    world.addEntity(new Player(client.username, world), 0)

    updateRenderStates()

    println("Accepted connection from " + client.username)
    true -> "Connection accepted"
  }

  def disconnect(client: RMIClient) {
    clients -= client
    println(client.username + " disconnected")

    updateRenderStates()
  }

  def playerList: Seq[String] = {
    clients.map(_.username).toSeq
  }

  def getRenderState(client: RMIClient): RenderState = {
    world.getRenderStateFor(client.username)
  }

  def handleEvent(client: RMIClient, e: Object) {

    val player = world.getPlayer(client.username)
    e match {
      case Key.Up => player.move(Direction.Up)
      case Key.Down => player.move(Direction.Down)
      case Key.Left => player.move(Direction.Left)
      case Key.Right => player.move(Direction.Right)
    }

    updateRenderStates()
  }

  def updateRenderStates() {
    clients.foreach(client => {
      client.updateRenderState(getRenderState(client))
    })
  }
}
