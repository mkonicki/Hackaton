package routes

import akka.actor.ActorSystem
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.Materializer
import ch.megard.akka.http.cors.CorsDirectives._

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by bartosz on 07.04.17.
  */
trait DzidaRoutes extends Directives {


  implicit val system: ActorSystem

  implicit def executor: ExecutionContextExecutor

  implicit val materializer: Materializer

  val routes = route


  def route: Route = cors() {
    path("dzida") {
      get {
        complete("it works!")
      }
    }
  }
}
