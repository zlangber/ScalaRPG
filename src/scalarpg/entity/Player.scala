package scalarpg.entity

import eventbus.EventHandler
import scalarpg.world.World
import scalarpg.animation.SpriteCache
import scalarpg.util.Direction
import swing.event.{Key, KeyPressed}
import java.awt.Point

class Player(world: World) extends Entity(world) {

  animationState.sprite = SpriteCache("player")

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
      val chunk = world.getChunkTowards(direction)
      if (chunk.isDefined) {
        repositionPlayer(direction)
        world.activeChunk = chunk.get
      }
      false
    } else super.canMoveTo(pos)
  }

  @EventHandler
  def keyPress(event: KeyPressed) {

    event.key match {
      case Key.Down => move(Direction.Down)
      case Key.Left => move(Direction.Left)
      case Key.Right => move(Direction.Right)
      case Key.Up => move(Direction.Up)
      case _ =>
    }
  }
}
