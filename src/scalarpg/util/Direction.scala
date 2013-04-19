package scalarpg.util

import scala.util.Random

object Direction extends Enumeration {

  type Direction = Value
  val Up, Down, Left, Right, None = Value

  def random(): Direction.Value = {
    val index = Random.nextInt(4)
    val arr = values.toArray
    arr(index)
  }
}
