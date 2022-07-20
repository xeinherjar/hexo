package hexo

import org.scalajs.dom
import org.scalajs.dom.document
import cats.effect.{IOApp, IO}
import cats.effect.std.Random
import cats.effect.implicits._
import fs2.Stream
import org.scalajs.dom.Event
import cats.effect.std.Queue
import cats.effect.implicits._
import cats.syntax._
import cats.implicits._
import cats.effect.std.Console
import cats.effect.kernel.Async
// import fs2.Chunk.Queue

object IOMain extends IOApp.Simple {
  override def run: IO[Unit] = {
    // TODO: I assume I should wrap these in IO
    implicit val rnd: Random[IO] = Random.javaUtilConcurrentThreadLocalRandom
    val canvas = document
      .createElement("canvas")
      .asInstanceOf[dom.html.Canvas]
    val ctx = canvas
      .getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]
    document.body.appendChild(canvas)

    // document.addEventListener("click", (e: Event) => IO.println(s"HI!, $e")).pure
    asdf[IO](document.body) *> IO.println("maybe")
    // document.body.onclick{ e: dom.MouseEvent => IO.println(s"HI!, $e")}.pure

    // IO.println("Hello world!") *> Program[IO](ctx).go()
    // val q = Queue.circularBuffer[IO, Event](50)
    // q.flatMap(queue =>
    //   document.body
    //     .addEventListener(
    //       "click",
    //       // (e: Event) => IO.defer(queue.offer(e)).unsafeRunAndForget()(using runtime),
    //       (e: Event) => IO.println("hi") *> queue.offer(e),
    //       false
    //     )
    //     .pure
    // )

    // Stream
    //   .eval(q)
    //   .flatMap(Stream.fromQueueUnterminated(_))
    //   .evalMap((e: Event) => IO.println(e))
    //   .compile
    //   .drain

  }
  def asdf[F[_]: Async: Console](el: dom.Element): F[Unit] = el.addEventListener("click", (e: Event) =>
    for {
    _ <- Console[F].println("hi")
    } yield ()
  ).pure
}

