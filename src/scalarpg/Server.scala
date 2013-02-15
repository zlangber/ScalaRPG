package scalarpg

import actors.Actor
import collection.mutable
import java.io._
import java.net.{Socket, ServerSocket}

object Server {

  case class Client(username: String, socket: Socket, inputStream: ObjectInputStream, outputStream: ObjectOutputStream)
  case object Accepted
  case object Rejected

  val clients = new mutable.HashMap[String, Client]() with mutable.SynchronizedMap[String, Client]

  def handleClient(client: Client) {

    try {
      val event = client.inputStream.readObject()
      for ((username, oclient) <- clients; if client.username != oclient.username) {
        oclient.outputStream.writeObject(event)
        oclient.outputStream.flush()
      }

      handleClient(client)
    }
    catch {
      case e: Throwable => {
        clients.remove(client.username)
        e.printStackTrace()
        println("Connection reset: " + client.socket)
      }
    }
  }

  def handleConnection(socket: Socket) {

    val outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()))
    outputStream.flush()
    val inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))

    val username = inputStream.readObject().asInstanceOf[String]
    if (clients.contains(username)) {
      println("Rejected: " + username + "@" + socket.getInetAddress + ":" + socket.getLocalPort)
      outputStream.writeObject(Rejected)
      outputStream.flush()
      socket.close()
    }
    else {
      println("Accepted: " + username + "@" + socket.getInetAddress + ":" + socket.getLocalPort)
      outputStream.writeObject(Accepted)
      outputStream.flush()

      val client = Client(username, socket, inputStream, outputStream)
      clients += username -> client
      handleClient(client)
    }
  }

  def start(port: Int) {

    println("Binding to port " + port + "...")
    val server = new ServerSocket(port)

    while (true) {
      val socket = server.accept()
      Actor.actor {
        handleConnection(socket)
      }
    }
  }

  def handleCommand(cmd: String) {

    cmd match {
      case "exit" => sys.exit()
      case _ => println("Invalid command")
    }
  }

  def main(args: Array[String]) {

    println("Starting ScalaRPG server...")
    val port = if (args.nonEmpty) args(0).toInt else 8000
    Actor.actor { start(port) }

    while (true) {
      handleCommand(readLine())
    }
  }
}
