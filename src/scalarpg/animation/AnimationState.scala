package scalarpg.animation

import scalarpg.util.{TickCounter, Direction}
import collection.immutable.HashMap

class AnimationState(sprite: Sprite, frameIndices: HashMap[Direction.Value, Array[Int]], delay: Int) {

  println(sprite)

  val counter = new TickCounter()
  var index = 0

  def tick() {

    if (counter.count() >= delay) {

      if (index >= frameIndices(Direction.Down).length - 1) index = 0
      else index += 1

      counter.reset()
    }

    counter.tick()
  }

 def getNextSpriteIndex(direction: Direction.Value):Int = {
    frameIndices(direction)(index)
 }
}