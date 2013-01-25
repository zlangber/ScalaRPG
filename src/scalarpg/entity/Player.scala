package scalarpg.entity

import scalarpg.world.World
import scalarpg.animation.SpriteCache

class Player(world: World) extends Entity(world) {

  println("setting sprite")
  sprite = SpriteCache("player")
}
