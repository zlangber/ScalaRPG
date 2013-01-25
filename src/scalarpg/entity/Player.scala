package scalarpg.entity

import eventbus.EventHandler
import scalarpg.world.World
import scalarpg.animation.SpriteCache
import scalarpg.util.Direction
import swing.event.{Key, KeyReleased, KeyPressed}

class Player(world: World) extends Entity(world) {

  sprite = SpriteCache("player")

  @EventHandler
  def keyPress(event: KeyPressed) {

    event.key match {
      case Key.Down => animationState.start(Direction.Down)
      case Key.Left => animationState.start(Direction.Left)
      case Key.Right => animationState.start(Direction.Right)
      case Key.Up => animationState.start(Direction.Up)
      case _ =>
    }
  }

  @EventHandler
  def keyReleased(event: KeyReleased) {
    println(event)
  }
}
