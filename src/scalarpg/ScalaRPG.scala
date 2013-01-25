package scalarpg

import _root_.eventbus.EventHandler
import eventbus.event.TickEvent
import eventbus.EventBusService
import gui.{IntroPanel, GamePanel}
import swing._
import world.World

object ScalaRPG {

  val world = new World()

  var isRunning = false
  val gameTimer = new javax.swing.Timer(50, Swing.ActionListener(e => EventBusService.publish(TickEvent())))

  val gamePanel = new GamePanel()
  val introPanel = new IntroPanel()
  val frame = new MainFrame {
    title = "ScalaRPG"
    contents = introPanel
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

    EventBusService.subscribe(this)

    frame.open()
    introPanel.requestFocus()
  }
}
