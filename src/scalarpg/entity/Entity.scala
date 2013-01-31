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

  val position = new Point(0, 0)

  var moveSpeed = 4
  var moving = false
  var direction = Direction.None
  val moveCounter = new TickCounter()

  var sprite = SpriteCache("missing")
  lazy val animationState = new AnimationState(this, 2)

  def canMoveTo(pos: Point):Boolean = {

    if (pos.x < 0 || pos.x > 16 * 32 || pos.y < 0 || pos.y > 16 * 32) false

    val chunk = world.activeChunk
    if (chunk.getTileAt(pos.x, pos.y).solid) false
    else true
  }

  def getPositionToward(direction : Direction.Value):Point = {

    var x = position.x
    var y = position.y
    direction match {
      case Direction.Down => y += 32
      case Direction.Left => x -= 32
      case Direction.Right => x += 32
      case Direction.Up => y -= 32
    }

    new Point(x, y)
  }

  def move(direction: Direction.Value) {

    if (!canMoveTo(getPositionToward(direction))) return

    if (moving) return

    this.direction = direction
    moving = true
    animationState.start("walk", direction)
  }

  def stop() {
    moving = false
    animationState.stop()
  }

  @EventHandler
  def tick(event: TickEvent) {

    if (dead) return

    if (moving) {

      if (moveCounter.count() > 32 / moveSpeed) {
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
