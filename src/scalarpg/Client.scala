package scalarpg

import java.io.IOException
import java.rmi.Naming
import java.rmi.server.UnicastRemoteObject
import javax.swing.Timer
import scala.swing._
import scalarpg.gui.{GameFrame, GamePanel, IntroPanel}
import scalarpg.render.{RenderStateImpl, RenderState}
import scalarpg.rmi.client.RMIClient
import scalarpg.rmi.server.RMIServer

object Client extends UnicastRemoteObject with RMIClient {

  var server: RMIServer = null
  var username = ""

  val timer = new Timer(50, Swing.ActionListener(e => tick()))
  var renderState: RenderState = new RenderStateImpl()

  val frame = new GameFrame()
  val introPanel = new IntroPanel()
  val gamePanel = new GamePanel

  def tick() {
    renderState.tick()
    gamePanel.repaint()
  }

  override def updateRenderState(renderState: RenderState) {
    this.renderState = renderState
  }

  def connect(host: String, username: String) {

    this.username = username
    try {
      Naming.lookup("rmi://" + host + "/ScalaRPGServer") match {
        case server: RMIServer =>
          this.server = server
          val (isSuccessful, message) = server.connect(this)
          println(message)
          if (isSuccessful)
            start(server.getRenderState(this))
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
    this.renderState = renderState
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
