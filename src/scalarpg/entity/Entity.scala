package scalarpg.entity

import java.awt.{Graphics2D, Point}
import java.util.UUID
import scalarpg.util.{TickCounter, Direction}
import scalarpg.animation.AnimationState
import scalarpg.world.World

abstract class Entity(@transient world: World) extends Serializable {

  val id = UUID.randomUUID()

  var dead = false

  val position = new Point(math.floor(math.random * 16).toInt * 32, math.floor(math.random * 16).toInt * 32)

  var moveSpeed = 4
  var moving = false
  var direction = Direction.None
  val moveCounter = new TickCounter()

  lazy val animationState = new AnimationState("missing", 2)

  def reposition(direction: Direction.Value) {

    direction match {
      case Direction.Down => position.y = 0
      case Direction.Left => position.x = 15 * 32
      case Direction.Right => position.x = 0
      case Direction.Up => position.y = 15 * 32
    }

    animationState.face(direction)
  }

  def canMoveTo(pos: Point): Boolean = {

    var direction = Direction.None
    if (pos.x < 0) direction = Direction.Left
    else if (pos.x >= 16 * 32) direction = Direction.Right
    else if (pos.y < 0) direction = Direction.Up
    else if (pos.y >= 16 * 32) direction = Direction.Down

    if (direction != Direction.None) {
      world.moveEntity(this, direction)
      return false
    }

    val (row, column) = world.getChunkCoordsFromIndex(world.entityChunkMap(id))
    val chunk = world.chunks(row)(column)

    if (chunk.getTileAt(pos.x, pos.y).solid) false
    else true
  }

  def getPositionToward(direction: Direction.Value): Point = {

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
    world.forceRenderUpdate()
  }

  def stop() {

    moving = false
    animationState.stop()
  }

  def tick() {

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

    animationState.tick()
  }
}
