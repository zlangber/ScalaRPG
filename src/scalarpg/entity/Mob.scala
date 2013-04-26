package scalarpg.entity

import scalarpg.Server
import scalarpg.util.{Direction, TickCounter}
import scalarpg.world.World

class Mob(name: String, world: World) extends Entity(world) {

  animationState.spriteKey = name

  val aiCounter = new TickCounter

  override def tick() {
    super.tick()

    if (!Server.isServer)
      return

    if (aiCounter.count() > 32 && math.random > .2) {
      aiCounter.reset()
      move(Direction.random())
    }

    aiCounter.tick()
  }
}
