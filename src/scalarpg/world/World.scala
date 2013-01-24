package scalarpg.world

import scalarpg.entity.Player
import scalarpg.eventbus.EventBusService

class World {

  val player = new Player(this)
  EventBusService.subscribe(player)
}
