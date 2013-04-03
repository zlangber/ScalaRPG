package scalarpg.gui

import scala.Some
import scala.swing.event.WindowClosing
import scalarpg.Client
import swing._

class GameFrame extends MainFrame {

  title = "ScalaRPG"
  contents = new Panel {
    preferredSize = new Dimension(512, 512)
  }
  menuBar = new MenuBar {
    contents += new Menu("File") {
      contents += new MenuItem(Action("Connect")({
        val hostInput = Dialog.showInput(null, "Host", "Server", Dialog.Message.Question, null, Nil, "")
        hostInput match {
          case Some(host) =>
            val name = Dialog.showInput(null, "Username", "", Dialog.Message.Question, null, Nil, "")
            Client.connect(host, name.get)
          case _ =>
        }
      }))
      contents += new MenuItem(Action("Disconnect")({
        disconnect()
        Client.stop()
      }))
      contents += new MenuItem(Action("Quit")({
        disconnect()
        sys.exit()
      }))
    }
  }
  listenTo(this)
  reactions += {
    case e: WindowClosing => disconnect()
  }
  resizable = false
  centerOnScreen()

  def disconnect() = if (Client.server != null) Client.server.disconnect(Client)
}
