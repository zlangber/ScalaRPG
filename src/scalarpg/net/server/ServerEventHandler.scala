package scalarpg.net.server

import scalarpg.eventbus.event.{EntityMovedEvent, KeyEvent}
import scalarpg.eventbus.{EventHandler, EventBusService}
import scalarpg.world.World
import scala.swing.event.Key
import scalarpg.util.Direction

object ServerEventHandler {

  var server: RMIServer = null

  @EventHandler
  def keyEvent(e: KeyEvent) {

    var direction: Direction.Value = Direction.None
    e.key match {
      case Key.Up => direction = Direction.Up
      case Key.Down => direction = Direction.Down
      case Key.Left => direction = Direction.Left
      case Key.Right => direction = Direction.Right
      case _ =>
    }

    server.sendEventToAll(new EntityMovedEvent(e.source.player, direction))
  }
}
