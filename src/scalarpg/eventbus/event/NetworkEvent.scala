package scalarpg.eventbus.event

abstract class NetworkEvent[+T](source: T) extends Event[T](source)