package scalarpg.render

import scala.collection.mutable
import java.io.{ObjectInputStream, ObjectOutputStream, IOException}

class RenderStateImpl extends RenderState with Serializable {

  protected override var renderers = mutable.PriorityQueue[Renderer]()

  override def +=(renderer: Renderer) {
    renderers += renderer
  }

  implicit def orderedRenderer(r: Renderer): Ordered[Renderer] = new Ordered[Renderer] {
    val order = mutable.IndexedSeq("ChunkRenderer", "EntityRenderer")
    def compare(that: Renderer): Int = order.indexOf(that.getClass.getName) - order.indexOf(r.getClass.getName)
  }

  @throws(classOf[IOException])
  private def writeObject(out: ObjectOutputStream): Unit = {
    out.writeObject(renderers.toList)
  }

  @throws(classOf[IOException])
  private def readObject(in: ObjectInputStream): Unit = {
    in.readObject() match {
      case r: List[Renderer] =>
        renderers = new mutable.PriorityQueue[Renderer]() ++ r
      case _ =>
    }
  }

}