package scalarpg.eventbus.event

import scalarpg.util.Direction
import scalarpg.entity.Player

class PlayerLeftChunkEvent(source: Player, val direction: Direction.Value) extends PlayerEvent(source)
