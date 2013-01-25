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
  private var index = 0
  private var direction = Direction.Down
  private val frameIndices = HashMap(
    Direction.Down -> Array(0, 1, 2),
    Direction.Left -> Array(8, 9, 10),
    Direction.Right -> Array(16, 17, 18),
    Direction.Up -> Array(24, 25, 26)
  )
  val counter = new TickCounter()

  EventBusService.subscribe(this)

  def start(dir: Direction.Value) {
    isAnimating = true
    direction = dir
  }

  def stop() {
    isAnimating = false
  }

  def currentFrame():BufferedImage = {
    entity.sprite(frameIndices(direction)(index))
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