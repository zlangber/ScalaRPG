package scalarpg.eventbus

import eventbus.BasicEventBus

object EventBusService {

  private val eventBus = new BasicEventBus()

  def subscribe(subscriber: Object) = eventBus.subscribe(subscriber)
  def unsubscribe(subscriber: Object) = eventBus.unsubscribe(subscriber)
  def publish(event: Object) = eventBus.publish(event)
}