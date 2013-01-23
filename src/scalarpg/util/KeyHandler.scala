package scalarpg.util

import swing.event.{Key, KeyReleased, KeyPressed}
import collection.mutable.HashMap

object KeyHandler {

  val keys = List(
    Key.Up,
    Key.Down,
    Key.Left,
    Key.Right,
    Key.Space
  )

  val keyState = new HashMap[Key.Value, Boolean]()
  keys.foreach(keyState.put(_, false))

  def apply(key: Key.Value):Boolean = {
    keyState(key)
  }

  def onKeyPressed(e: KeyPressed) {
    keyState(e.key) = true
  }

  def onKeyReleased(e: KeyReleased) {
    keyState(e.key) = false
  }

}
