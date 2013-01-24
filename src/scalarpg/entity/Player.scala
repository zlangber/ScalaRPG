package scalarpg.entity

import swing.event.Key
import scalarpg.util.{KeyHandler, Direction}
import scalarpg.world.World

class Player(world: World) extends Entity(world) {

  sprite.load("player.png")
  spriteIndex = 1

  override def move() {

    super.move()

    if (isMoving) return

    if (KeyHandler(Key.Up)) direction = Direction.Up
    else if (KeyHandler(Key.Down)) direction = Direction.Down
    else if (KeyHandler(Key.Left)) direction = Direction.Left
    else if (KeyHandler(Key.Right)) direction = Direction.Right
  }


  override def reachedDestination(): Boolean = {
    val res = super.reachedDestination()
    //if (res) world.updateView()
    res
  }
}
