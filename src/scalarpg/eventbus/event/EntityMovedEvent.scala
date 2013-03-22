package scalarpg.eventbus.event

import scalarpg.entity.Entity
import scalarpg.util.Direction

class EntityMovedEvent(override val source: Entity, val direction: Direction.Value) extends NetworkEvent[Entity](source)
