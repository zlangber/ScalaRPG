package scalarpg.eventbus.event

import scalarpg.entity.Player
import scalarpg.util.Direction

class PlayerLeftChunkEvent(source: Player, val direction: Direction.Value) extends PlayerEvent(source)
