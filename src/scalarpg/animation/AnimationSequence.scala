package scalarpg.animation

import scalarpg.traits.Tickable
import scalarpg.util.{TickCounter, Direction}
import collection.immutable.HashMap

class AnimationSequence(sprite: Sprite, frameIndices: HashMap[Direction.Value, Array[Int]], delay: Int) extends Tickable {

  val counter = new TickCounter()
  var index = frameIndices(Direction.Down)(0)

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