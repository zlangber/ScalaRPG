package scalarpg.eventbus.event

import scalarpg.net.client.RMIClient
import scala.swing.event.Key

class KeyEvent(override val source: RMIClient, val key: Key.Value, val modifiers: Key.Modifiers) extends NetworkEvent[RMIClient](source)