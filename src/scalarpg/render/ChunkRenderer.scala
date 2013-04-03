package scalarpg.render

import java.awt.Graphics2D
import scalarpg.world.Chunk

class ChunkRenderer(chunk: Chunk) extends Renderer {

  def tick() {}

  def render(g: Graphics2D) {
    chunk.tiles.flatten.foreach(_.paint(g))
  }
}
