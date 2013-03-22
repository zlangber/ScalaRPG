package scalarpg.eventbus.event

import scalarpg.entity.Player

class PlayerDisconnectedEvent(source: Player) extends PlayerEvent(source)
