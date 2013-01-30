package scalarpg.entity

import eventbus.EventHandler
import scalarpg.world.World
import scalarpg.animation.SpriteCache
import scalarpg.util.Direction
import swing.event.{Key, KeyPressed}
import java.awt.Point

class Player(world: World) extends Entity(world) {

  sprite = SpriteCache("player")

  def repositionPlayer(direction: Direction.Value) {

    direction match {
      case Direction.Down => position.y = 0
      case Direction.Left => position.x = 15 * 32
      case Direction.Right => position.x = 0
      case Direction.Up => position.y = 15 * 32
    }
  }

  override def canMoveTo(pos: Point): Boolean = {

    if (pos.x < 0) {
      val chunk = world.getChunkTowards(Direction.Left)
      if (chunk.isDefined) {
        repositionPlayer(Direction.Left)
        world.activeChunk = chunk.get
      }
      false
    } else if (pos.x >= 16 * 32) {
      val chunk = world.getChunkTowards(Direction.Right)
      if (chunk.isDefined) {
        repositionPlayer(Direction.Right)
        world.activeChunk = chunk.get
      }
      false
    } else if (pos.y < 0) {
      val chunk = world.getChunkTowards(Direction.Up)
      if (chunk.isDefined) {
        repositionPlayer(Direction.Up)
        world.activeChunk = chunk.get
      }
      false
    } else if (pos.y >= 16 * 32) {
      val chunk = world.getChunkTowards(Direction.Down)
      if (chunk.isDefined) {
        repositionPlayer(Direction.Down)
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
