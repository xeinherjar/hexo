package hexo

import org.scalajs.dom
import org.scalajs.dom.document
import cats.effect.{IOApp, IO}
import cats.effect.std.Random
import cats.effect.implicits._
import fs2._
import org.scalajs.dom.Event
import cats.effect.std.Queue
import cats.effect.implicits._
import cats.syntax._
import cats.implicits._
import cats.effect.std.Console
import cats.effect.Async
import cats.effect.Concurrent
import cats.effect.std.{Dispatcher, Queue}
import cats.effect.ExitCode
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
    // IO.println("Hello world!") *>
    (
      Program[IO](ctx).go(),
      (for {
        dispatcher <- Stream.resource(Dispatcher[IO])
        q <- Stream.eval(Queue.unbounded[IO, Event])
        _ <- Stream.eval {
          IO.delay {
            document
              .addEventListener(
                "click",
                (e: Event) => dispatcher.unsafeRunAndForget(q.offer(e))
              )
          }
        }
        _ <- Stream
          .fromQueueUnterminated(q)
          .evalMap(e => dispatcher.unsafeRunAndForget(IO.println(s"Yay? $e??")).pure[IO])
      } yield ()).compile.drain
    ).parTupled.void

  }
}
