package scalarpg.animation

import eventbus.EventHandler
import scalarpg.util.{TickCounter, Direction}
import collection.immutable.HashMap
import scalarpg.eventbus.EventBusService
import scalarpg.entity.Entity
import java.awt.image.BufferedImage
import scalarpg.traits.TickListener
import scalarpg.eventbus.event.TickEvent

class AnimationState(entity: Entity, delay: Int) extends TickListener {

  var isAnimating = false
  val counter = new TickCounter()

  private var key = "walk"
  private var direction = Direction.Down
  private var index = 0

  private val frameIndices = HashMap(
    "walk" -> HashMap(
      Direction.Down -> Array(0, 1, 2),
      Direction.Left -> Array(8, 9, 10),
      Direction.Right -> Array(16, 17, 18),
      Direction.Up -> Array(24, 25, 26)
    )
  )

  EventBusService.subscribe(this)

  def start(k: String, dir: Direction.Value) {
    isAnimating = true
    key = k
    direction = dir
  }

  def stop() {
    isAnimating = false
  }

  def face(dir: Direction.Value) {

    direction = dir
    index = frameIndices("walk")(dir)(0)
  }

  def currentFrame():BufferedImage = {
    entity.sprite(frameIndices(key)(direction)(index))
  }

  @EventHandler
  def tick(event: TickEvent) {

    if (!isAnimating) {
      index = 0
      return
    }

    if (counter.count() >= delay) {
      index = if (index == 1) 2 else 1
      counter.reset()
    }

    counter.tick()
  }
}