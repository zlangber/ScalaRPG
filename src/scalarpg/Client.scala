package scalarpg

import java.io.IOException
import java.rmi.Naming
import javax.swing.Timer
import scala.swing._
import scalarpg.client.{RMIClientImpl, RMIClient}
import scalarpg.eventbus.EventBusService
import scalarpg.eventbus.event.TickEvent
import scalarpg.gui.{GameFrame, GamePanel, IntroPanel}
import scalarpg.server.RMIServer
import scalarpg.world.World
import scalarpg.entity.Player

object Client {

  var rmiClient: RMIClient = null;

  val gamePanel = new GamePanel()
  val introPanel = new IntroPanel()
  val frame = new GameFrame(introPanel)

  val timer = new Timer(50, Swing.ActionListener(e => EventBusService.publish(TickEvent())))

  val world = new World()

  def showTitleScreen() {
    frame.contents = introPanel
  }

  def start(worldData: xml.Node) {
    frame.contents = gamePanel
    gamePanel.requestFocus()
    world.load(worldData)
    timer.start()
  }

  def connectToServer(host: String, username: String) {

    try {
      Naming.lookup("rmi://" + host + "/ScalaRPGServer") match {
        case server: RMIServer =>
          rmiClient = new RMIClientImpl(new Player(world, username), server)
          rmiClient.connect()
        case _ => println("Could not connect to server at " + host)
      }
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
        Dialog.showMessage(null, "Failed to connect to server " + host, "Failed to connect!", Dialog.Message.Error, null)
        if (rmiClient != null) rmiClient.disconnect()
    }
  }

  def main(args: Array[String]) {
    frame.open()
  }
}
