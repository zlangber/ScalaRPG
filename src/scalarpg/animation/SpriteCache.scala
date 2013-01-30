package scalarpg.animation

import collection.immutable.HashMap

object SpriteCache {

  private val cache = HashMap(
    "missing" -> new Sprite("missing.png"),
    "player" -> new Sprite("player.png"),
    "world" -> new Sprite("world.png"),
    "structure" -> new Sprite("structure.png")
  )

  def apply(name:String):Sprite = {
    cache(name)
  }
}
