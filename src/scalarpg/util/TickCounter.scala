package scalarpg.util

class TickCounter extends Serializable {

  private var currentTick = 0

  def count() = currentTick
  def tick() = currentTick += 1
  def reset() = currentTick = 0
}
