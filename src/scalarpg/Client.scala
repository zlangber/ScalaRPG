package scalarpg

import java.io.IOException
import java.rmi.Naming
import javax.swing.Timer
import scala.swing._
import scalarpg.entity.Player
import scalarpg.eventbus.event.{RepaintEvent, TickEvent}
import scalarpg.eventbus.{EventHandler, EventBusService}
import scalarpg.gui.{GameFrame, GamePanel, IntroPanel}
import scalarpg.net.client.{RMIClient, RMIClientImpl}
import scalarpg.net.server.RMIServer
import scalarpg.world.{Chunk, WorldClient}

object Client {

  var rmiClient: RMIClient = null

  val gamePanel = new GamePanel()
  val introPanel = new IntroPanel()
  val frame = new GameFrame(introPanel)

  val timer = new Timer(50, Swing.ActionListener(e => EventBusService.publish(new TickEvent())))

  def showTitleScreen() {
    frame.contents = introPanel
  }

  def start() {
    frame.contents = gamePanel
    gamePanel.requestFocus()
    timer.start()
  }

  def connectToServer(host: String, username: String) {

    try {
      Naming.lookup("rmi://" + host + "/ScalaRPGServer") match {
        case server: RMIServer =>
          rmiClient = new RMIClientImpl(new Player(username), server)
          rmiClient.connect()
        case _ => println("Could not connect to server at " + host)
      }
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
        Dialog.showMessage(null, "Error connecting to server " + host, "Failed to connect!", Dialog.Message.Error, null)
        if (rmiClient != null) rmiClient.disconnect()
    }
  }

  def main(args: Array[String]) {
    frame.open()
  }
}
