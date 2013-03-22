package scalarpg.eventbus.event

import scalarpg.entity.Entity

class EntityEvent(override val source: Entity) extends NetworkEvent[Entity](source)
