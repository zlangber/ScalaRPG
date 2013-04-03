package scalarpg

import java.rmi.Naming
import java.rmi.registry.LocateRegistry
import scalarpg.rmi.server.RMIServerImpl

object Server {

  val server = new RMIServerImpl()

  val startTime = System.nanoTime

  def handleCommand(cmd: String) {

    cmd match {
      case "players" => println("Players:"); server.playerList.foreach(p => println("\t" + p))
      case "uptime" => println(((System.nanoTime - startTime) / 1e9).toInt + "s")
      case "exit" => sys.exit()
      case _ => println("Unknown command")
    }
  }

  def main(args: Array[String]) {

    println("Starting ScalaRPG server...")
    LocateRegistry.createRegistry(1099)
    Naming.bind("ScalaRPGServer", server)

    while (true) {
      handleCommand(readLine())
    }
  }
}
