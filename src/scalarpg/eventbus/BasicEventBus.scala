package eventbus

import java.lang.reflect.Method
import ref.WeakReference
import collection.mutable.ListBuffer

class BasicEventBus extends EventBus {

  case class HandlerInfo(eventClass: Class[_], method: Method, subscriber: WeakReference[Object]) {
    def matchesEvent(event: Object): Boolean = eventClass.isAssignableFrom(event.getClass)
  }

  val handlers = new ListBuffer[HandlerInfo]()

  def subscribe(subscriber: Object) {

    val methods = subscriber.getClass.getDeclaredMethods
    methods.foreach(m => {

      val eventHandler = m.getAnnotation(classOf[EventHandler])
      if (eventHandler != null) {

        val params = m.getParameterTypes
        if (params.length != 1) {
          throw new IllegalArgumentException("EventHandler methods must specify a single object parameter.")
        }

        handlers += HandlerInfo(params(0), m, new WeakReference[Object](subscriber))
      }
    })
  }

  def unsubscribe(subscriber: Object) {

    handlers.foreach(handlerInfo => {
      val s = handlerInfo.subscriber.get.get
      if (s == null || s == subscriber) {
        handlers -= handlerInfo
      }
    })
  }

  def publish(event: Object) {

    handlers.foreach(handlerInfo => {
      if (handlerInfo.matchesEvent(event)) {
        val subscriber = handlerInfo.subscriber.get.get
        if (subscriber != null) {
          handlerInfo.method.invoke(subscriber, event)
        } else {
          handlers -= handlerInfo
        }
      }
    })
  }
}
