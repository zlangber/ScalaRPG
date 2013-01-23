package eventbus

trait EventBus {

  def subscribe(subscriber: Object)
  def unsubscribe(subscriber: Object)
  def publish(event: Object)
}