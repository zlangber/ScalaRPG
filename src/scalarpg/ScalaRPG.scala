package scalarpg

import _root_.eventbus.BasicEventBus
import gui.{IntroPanel, GamePanel}
import swing._
import traits.RepaintListener
import world.World

object ScalaRPG extends RepaintListener {

  val eventBus = new BasicEventBus()

  val world = new World()

  var isRunning = false
  val gameTimer = new javax.swing.Timer(20, Swing.ActionListener(f => { tick() }))

  val gamePanel = new GamePanel(this)
  val introPanel = new IntroPanel()
  val frame = new MainFrame {
    title = "ScalaRPG"
    contents = introPanel
    centerOnScreen()
  }

  def tick() {

    if (!isRunning) {
      gameTimer.stop()
      return
    }

    world.tick()
    gamePanel.repaint()

  }

  def onRepaint(g: Graphics2D) {
    world.paint(g)
  }

  def start() {

    isRunning = true
    frame.contents = gamePanel
    gamePanel.requestFocus()
    gameTimer.start()
  }

  def main(args: Array[String]) {

    frame.open()
    introPanel.requestFocus()
  }
}
