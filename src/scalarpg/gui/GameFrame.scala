package scalarpg.gui

import scala.Some
import scala.swing.event.WindowClosing
import scalarpg.Client
import swing._

class GameFrame(p: Panel) extends MainFrame {

  title = "ScalaRPG"
  contents = p
  menuBar = new MenuBar {
    contents += new Menu("File") {
      contents += new MenuItem(Action("Connect")({
        val hostInput = Dialog.showInput(null, "Host", "Server", Dialog.Message.Question, null, Nil, "")
        hostInput match {
          case Some(host) =>
            val name = Dialog.showInput(null, "Username", "", Dialog.Message.Question, null, Nil, "")
            Client.connectToServer(host, name.get)
          case _ =>
        }
      }))
      contents += new MenuItem(Action("Disconnect")({
        if (Client.rmiClient != null) Client.rmiClient.disconnect()
      }))
      contents += new MenuItem(Action("Quit")({
        if (Client.rmiClient != null) Client.rmiClient.disconnect()
        sys.exit()
      }))
    }
  }
  listenTo(this)
  reactions += {
    case e: WindowClosing => if (Client.rmiClient != null) Client.rmiClient.disconnect()
  }
  resizable = false
  centerOnScreen()
}
