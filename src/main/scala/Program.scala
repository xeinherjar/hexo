package hexo

import cats.effect.Async
import cats.effect.std.Console
import cats.effect.std.Random
import cats.effect.Temporal
import org.scalajs.dom
import scala.concurrent.duration._
import cats.effect.implicits._
import cats.syntax._
import cats.implicits._

case class Program[F[_]: Async: Random: Console](ctx: dom.CanvasRenderingContext2D) {

  def go(): F[Unit] =
    (for {
      r <- Random[F].betweenInt(0, 256)
      g <- Random[F].betweenInt(0, 256)
      b <- Random[F].betweenInt(0, 256)
      _ <- Console[F].println(r, g, b)
    } yield {
      ctx.fillStyle = s"rgb($r, $g, $b)"
      ctx.fillRect(0, 0, ctx.canvas.clientHeight, ctx.canvas.clientWidth)
    }) *> Temporal[F].sleep(2.seconds) *> Async[F].defer(go())



}
