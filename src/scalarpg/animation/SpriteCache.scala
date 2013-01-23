package scalarpg.animation

import collection.immutable.HashMap

object SpriteCache {

  private val cache = HashMap(
    "missing.png" -> new Sprite("missing.png"),
    "player.png" -> new Sprite("player.png"),
    "world.png" -> new Sprite("world.png")
  )

  def apply(name:String):Sprite = {
    cache(name)
  }
}
