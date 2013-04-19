package scalarpg

import java.io.IOException
import java.rmi.Naming
import javax.swing.Timer
import scala.swing._
import scalarpg.gui.{GameFrame, GamePanel, IntroPanel}
import scalarpg.render.RenderState
import scalarpg.rmi.client.{RMIClientImpl, RMIClient}
import scalarpg.rmi.server.RMIServer

object Client {

  var rmiServer: RMIServer = null
  var rmiClient: RMIClient = null

  val timer = new Timer(50, Swing.ActionListener(e => tick()))

  val frame = new GameFrame()
  val introPanel = new IntroPanel()
  val gamePanel = new GamePanel

  def tick() {
    rmiClient.tick()
    gamePanel.repaint()
  }

  def connect(host: String, username: String) {

    rmiClient = new RMIClientImpl(username)
    try {
      Naming.lookup("rmi://" + host + "/ScalaRPGServer") match {
        case server: RMIServer =>
          rmiServer = server
          val (isSuccessful, message) = server.connect(rmiClient)
          println(message)
          if (isSuccessful)
            start(server.getRenderState(rmiClient))
        case _ => println("Could not connect to server at " + host)
      }
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
        Dialog.showMessage(null, "Error connecting to server " + host, "Failed to connect!", Dialog.Message.Error, null)
    }
  }

  def start(renderState: RenderState) {
    rmiClient.updateRenderState(renderState)
    frame.contents = gamePanel
    gamePanel.requestFocus()
    timer.start()
  }

  def stop() {
    timer.stop()
    showTitleScreen()
  }

  def showTitleScreen() {
    frame.contents = introPanel
    frame.repaint()
  }

  def main(args: Array[String]) {
    frame.open()
    showTitleScreen()
  }
}
