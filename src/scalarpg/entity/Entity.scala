package scalarpg.entity

import java.awt.{Graphics2D, Point}
import java.util.UUID
import scalarpg.util.{TickCounter, Direction}
import scalarpg.animation.AnimationState

abstract class Entity extends Serializable {

  val id = UUID.randomUUID()

  var dead = false

  val position = new Point(math.floor(math.random * 16).toInt * 32, math.floor(math.random * 16).toInt * 32)

  var moveSpeed = 4
  var moving = false
  var direction = Direction.None
  val moveCounter = new TickCounter()

  lazy val animationState = new AnimationState("missing", 2)

  def canMoveTo(pos: Point):Boolean = {

    if (pos.x < 0 || pos.x > 16 * 32 || pos.y < 0 || pos.y > 16 * 32) false

    //val chunk = world.activeChunk
    //if (chunk.getTileAt(pos.x, pos.y).solid) false
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

    if (moving) return

    if (!canMoveTo(getPositionToward(direction))) return

    this.direction = direction
    moving = true
    animationState.start("walk", direction)

    println("in move, " + moving + ", " + id)
  }

  def stop() {

    moving = false
    animationState.stop()
  }

  def tick() {

    if (dead) return

    if (moving) {

      println("moving")

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
}
