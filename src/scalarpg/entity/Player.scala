package scalarpg.entity

import java.awt.Point
import scalarpg.eventbus.EventHandler
import scalarpg.eventbus.event.RepaintEvent
import scalarpg.util.Direction
import swing.event.{Key, KeyPressed}

class Player(val username: String) extends Entity {

  animationState.spriteKey = "player"

  def repositionPlayer(direction: Direction.Value) {

    direction match {
      case Direction.Down => position.y = 0
      case Direction.Left => position.x = 15 * 32
      case Direction.Right => position.x = 0
      case Direction.Up => position.y = 15 * 32
    }

    animationState.face(direction)
  }

  override def canMoveTo(pos: Point): Boolean = {

    var direction = Direction.None

    if (pos.x < 0) direction = Direction.Left
    else if (pos.x >= 16 * 32) direction = Direction.Right
    else if (pos.y < 0) direction = Direction.Up
    else if (pos.y >= 16 * 32) direction = Direction.Down

    if (direction != Direction.None) {
      //EventBusService.publish(new PlayerLeftChunkEvent(this, direction))
      false
    } else super.canMoveTo(pos)
  }

  @EventHandler
  override def paint(event: RepaintEvent) {
    super.paint(event)
    event.graphics.drawString(username, position.x, position.y - 5)
  }
}
