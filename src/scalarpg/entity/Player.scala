package scalarpg.entity

import scalarpg.world.World
import scalarpg.animation.SpriteCache

class Player(world: World) extends Entity(world) {

  sprite = SpriteCache("player")
}
