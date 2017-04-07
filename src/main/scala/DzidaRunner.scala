import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}
import routes.DzidaRoutes

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by bartosz on 07.04.17.
  */
object DzidaRunner extends DzidaRoutes {
  override implicit val system: ActorSystem = ActorSystem()

  override implicit def executor: ExecutionContextExecutor = system.dispatcher

  override implicit val materializer: Materializer = ActorMaterializer()

  def main (args: Array[String]){
    Http().bindAndHandle(route, "0.0.0.0", 9000)
  }
}
