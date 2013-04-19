package scalarpg.animation

import collection.immutable.HashMap

object SpriteCache {

  private val cache = HashMap(
    "missing" -> new Sprite("missing.png"),
    "world" -> new Sprite("world.png"),
    "structure" -> new Sprite("structure.png"),
    "player" -> new Sprite("player.png"),
    "blueSlime" -> new Sprite("blueSlime.png")
  )

  def apply(name:String):Sprite = {
    cache(name)
  }
}
