package scalarpg.eventbus.event

import scalarpg.entity.Entity

class EntityEvent(source: Entity) extends Event[Entity](source)
