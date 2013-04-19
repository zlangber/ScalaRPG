package scalarpg.entity

import java.awt.Point
import scalarpg.util.Direction
import scalarpg.world.World

class Player(val username: String, world: World) extends Entity(world) {

  animationState.spriteKey = "player"
}
