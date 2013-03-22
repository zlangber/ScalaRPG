package scalarpg.net.client

import scalarpg.Client
import scalarpg.eventbus.event.{EntityMovedEvent, PlayerDisconnectedEvent, PlayerConnectedEvent}
import scalarpg.eventbus.{EventHandler, EventBusService}

object ClientEventHandler {

  private lazy val world = Client.rmiClient.world

  EventBusService.subscribe(this)

  @EventHandler
  def playerConnected(e: PlayerConnectedEvent) {
    world.addEntity(e.source, e.chunkIndex)
    println(e.source.username + " connected")
  }

  @EventHandler
  def playerDisconnected(e: PlayerDisconnectedEvent) {
    world.removeEntity(e.source)
  }

  @EventHandler
  def entityMoved(e: EntityMovedEvent) {
    println("moving entity with id " + e.source.id + " " + e.direction)
    val entity = world.getEntity(e.source.id)
    entity.move(e.direction)
    println(entity)
  }
}
