package scalarpg.world

import scalarpg.eventbus.EventHandler
import scalarpg.eventbus.event.RepaintEvent

class WorldClient(override val chunks: Array[Chunk], var activeChunkIndex: Int) extends World {

  @EventHandler
  def repaint(e: RepaintEvent) {
    chunks(activeChunkIndex).paint(e)
  }
}
