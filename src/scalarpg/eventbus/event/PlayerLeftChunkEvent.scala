package scalarpg.eventbus.event

import scalarpg.entity.Player

class PlayerLeftChunkEvent(source: Player) extends EntityEvent(source)
