package hexo

import org.scalajs.dom
import org.scalajs.dom.document
import cats.effect.{IOApp, IO}
import cats.effect.std.Random

object IOMain extends IOApp.Simple {
  override def run: IO[Unit] = {
    // TODO: I assume I should wrap these in IO
    val canvas = document
      .createElement("canvas")
    val ctx = canvas
      .asInstanceOf[dom.html.Canvas]
      .getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]
    document.body.appendChild(canvas)

    def foo() = {
      ctx.fillStyle = "rgb(1, 100, 200)"
      ctx.fillRect(0, 0, canvas.clientHeight, canvas.clientWidth)
    }

    IO.println("Hello world!") *>
      IO(foo())
  }
}
