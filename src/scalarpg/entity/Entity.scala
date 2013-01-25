package scalarpg.entity

import eventbus.EventHandler
import java.awt.Point
import scalarpg.util.{TickCounter, Direction}
import scalarpg.traits.{RepaintListener, TickListener}
import scalarpg.animation.{AnimationState, SpriteCache}
import scalarpg.world.World
import scalarpg.eventbus.event.{RepaintEvent, TickEvent}

abstract class Entity(world: World) extends TickListener with RepaintListener {

  var dead = false

  val position = new Point()

  var moveSpeed = 2
  var moving = false
  var direction = Direction.None
  val moveCounter = new TickCounter()

  var sprite = SpriteCache("missing")
  lazy val animationState = new AnimationState(this, 2)

  def move(direction: Direction.Value) {

    if (moving) return

    this.direction = direction
    moving = true
    animationState.start(direction)
  }

  def stop() {
    moving = false
    animationState.stop()
  }

  @EventHandler
  def tick(event: TickEvent) {

    if (dead) return

    if (moving) {

      if (moveCounter.count() >= 32 / moveSpeed) {
        stop()
        moveCounter.reset()
      }
      else {
        direction match {
          case Direction.Down => position.y += moveSpeed
          case Direction.Left => position.x -= moveSpeed
          case Direction.Right => position.x += moveSpeed
          case Direction.Up => position.y -= moveSpeed
        }
      }
      moveCounter.tick()
    }
  }

  @EventHandler
  def paint(event: RepaintEvent) {
    event.graphics.drawImage(animationState.currentFrame(), position.x, position.y, null)
  }
}
