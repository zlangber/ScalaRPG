package scalarpg.eventbus.event

import swing.Panel
import java.awt.Graphics2D

class RepaintEvent(source: Panel, val graphics: Graphics2D) extends Event[Panel](source)