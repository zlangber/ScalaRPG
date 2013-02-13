package scalarpg.eventbus.event

import scalarpg.entity.Player

class PlayerEvent(override val source: Player) extends EntityEvent(source)
