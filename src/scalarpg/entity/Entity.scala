package scalarpg.entity

import java.awt.{Graphics2D, Point}
import scalarpg.util.{TickCounter, Direction}
import scalarpg.traits.{Paintable, Tickable}
import collection.immutable.HashMap
import scalarpg.animation.{AnimationSequence, Sprite}
import scalarpg.world.World

abstract class Entity(world: World) extends Tickable with Paintable {

  val position = new Point()
  var prevPosition = new Point()

  var direction = Direction.None

  var moveSpeed = 2
  var isMoving = false

  val sprite = new Sprite("missing.png")
  var spriteIndex = 0

  val frameIndices = HashMap(
    Direction.Up -> Array(24, 25, 26),
    Direction.Down -> Array(0, 1, 2),
    Direction.Left -> Array(8, 9, 10),
    Direction.Right -> Array(16, 17, 18)
  )
  val animationSequence = new AnimationSequence(sprite, frameIndices, delay = 3)

  val counter = new TickCounter()

  var isDead = false

  def getTilePosition():Point = {
    new Point(position.x / 32, position.y / 32)
  }

  def move() {

    if (direction != Direction.None && !isMoving) {

      direction match {
        case Direction.Up => if (getTilePosition().y <= 0) return
        case Direction.Down => if (getTilePosition().y >= world.SIZE) return
        case Direction.Left => if (getTilePosition().x <= 0) return
        case Direction.Right => if (getTilePosition().x >= world.SIZE) return
      }

      prevPosition = new Point(position.x, position.y)
      isMoving = true
    }

    if (isMoving) {

      spriteIndex = animationSequence.getNextSpriteIndex(direction)

      direction match {
        case Direction.Up => position.y -= moveSpeed
        case Direction.Down => position.y += moveSpeed
        case Direction.Left => position.x -= moveSpeed
        case Direction.Right => position.x += moveSpeed
      }

      if (reachedDestination()) {
        isMoving = false
        spriteIndex = frameIndices(direction)(1)
        direction = Direction.None
      }
    }
  }

  def reachedDestination():Boolean =  {
     direction match {
       case Direction.Up => position.y == prevPosition.y - 32
       case Direction.Down => position.y == prevPosition.y + 32
       case Direction.Left => position.x == prevPosition.x - 32
       case Direction.Right => position.x == prevPosition.x + 32
     }
  }

  def tick() {

    if (isDead) return

    move()

    animationSequence.tick()
    counter.tick()
  }

  def paint(g: Graphics2D) {
    g.drawImage(sprite(spriteIndex), position.x, position.y, null)
  }
}
