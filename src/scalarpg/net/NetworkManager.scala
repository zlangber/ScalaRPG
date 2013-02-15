package scalarpg.net

import java.net.{InetSocketAddress, Socket}
import java.io._
import actors.Actor
import scalarpg.eventbus.EventBusService

class NetworkManager(val host: String, val port: Int) {

  private val socket = new Socket()
  private var ois: ObjectInputStream = null
  private var oos: ObjectOutputStream = null

  def listen() {
    Actor.actor {
      while (true) {
        val event = ois.readObject()
        println(event)
        EventBusService.publish(event)
      }
    }
  }

  def send(o: Object) {
    oos.writeObject(o)
    oos.flush()
  }

  def connect() {

    try {
      val address = new InetSocketAddress(host, port)
      socket.connect(address, 1000)
      oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()))
      oos.flush()
      ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))
      println("Connected to " + address)
    }
    catch {
      case e: Throwable => {
        println("Error connecting to server.")
        sys.exit()
      }
    }
  }

  def disconnect() {
    socket.close()
  }
}
