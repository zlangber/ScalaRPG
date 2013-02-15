package scalarpg

import _root_.eventbus.EventHandler
import eventbus.event.TickEvent
import eventbus.EventBusService
import gui.{IntroPanel, GamePanel}
import net.NetworkManager
import swing._
import world.World

object ScalaRPG {

  val networkManager = new NetworkManager("localhost", 8000)

  val world = new World()

  var isRunning = false
  val gameTimer = new javax.swing.Timer(50, Swing.ActionListener(e => EventBusService.publish(TickEvent())))

  val gamePanel = new GamePanel()
  val introPanel = new IntroPanel()
  val frame = new MainFrame {
    title = "ScalaRPG"
    contents = introPanel
    menuBar = new MenuBar {
      contents += new Menu("File") {
        contents += new MenuItem(Action("Save")())
        contents += new MenuItem(Action("Load")())
        contents += new MenuItem(Action("Quit")(System.exit(0)))
      }
    }
    centerOnScreen()
  }

  @EventHandler
  def onTick(event: TickEvent) {

    if (!isRunning) {
      gameTimer.stop()
      return
    }
  }

  def start() {

    isRunning = true

    frame.contents = gamePanel
    gamePanel.requestFocus()
    gameTimer.start()

    world.load("level0.xml")
  }

  def main(args: Array[String]) {

    println("Connecting to server..")
    networkManager.connect()
    networkManager.listen()
    networkManager.send(args(0))

    EventBusService.subscribe(this)

    frame.open()
    introPanel.requestFocus()
  }
}
