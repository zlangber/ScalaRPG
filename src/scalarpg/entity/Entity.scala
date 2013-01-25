package scalarpg.entity

import eventbus.EventHandler
import java.awt.Point
import scalarpg.util.Direction
import scalarpg.traits.{RepaintListener, TickListener}
import scalarpg.animation.{AnimationState, SpriteCache}
import scalarpg.world.World
import scalarpg.eventbus.event.{RepaintEvent, TickEvent}

abstract class Entity(world: World) extends TickListener with RepaintListener {

  val position = new Point()
  var direction = Direction.None

  var sprite = SpriteCache("missing")
  lazy val animationState = new AnimationState(this, 2)

  def move(direction: Direction.Value) {

  }

  @EventHandler
  def tick(event: TickEvent) {

  }

  @EventHandler
  def paint(event: RepaintEvent) {
    event.graphics.drawImage(animationState.currentFrame(), position.x, position.y, null)
  }
}
