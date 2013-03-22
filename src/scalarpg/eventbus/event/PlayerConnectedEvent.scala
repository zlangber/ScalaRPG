package scalarpg.eventbus.event

import scalarpg.entity.Player

class PlayerConnectedEvent(source: Player, val chunkIndex: Int) extends PlayerEvent(source)