package routes

import akka.actor.ActorSystem
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.Materializer
import ch.megard.akka.http.cors.CorsDirectives._
import charging.ChargingMediator
import db.BeaconInfoFacade
import json.{BeaconInfo, JsonSupport}
import spark.{AttractivePlace, SparkAnalyze}

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by bartosz on 07.04.17.
  */
trait DzidaRoutes extends Directives with JsonSupport{


  implicit val system: ActorSystem

  implicit def executor: ExecutionContextExecutor

  implicit val materializer: Materializer

  val routes = route


  def route: Route = cors() {
    path("dzida") {
      get {
        complete("it works!")
      }
    }~
    pathPrefix("beacon") {
      get {
        pathEnd {
          complete("tbd")
        } ~
          path(IntNumber) { int =>
            complete {
            "tbd"
            }
          }
      } ~
        post {
          entity(as[BeaconInfo]) { beaconInfo =>
            val beaconInfoFacade = new BeaconInfoFacade()
            beaconInfoFacade.save(beaconInfo)
            complete(beaconInfo)
          }
        }
    }~
    pathPrefix("chargingStatus"){
      get{
        path(IntNumber){int =>
          val chargingMediator = new ChargingMediator(int)
          complete{
            chargingMediator.createStatus()
          }
        }
      }
    }~
    pathPrefix("attractivePlace"){
      get{
        path(IntNumber){ int =>
          val attractivePlace = new AttractivePlace()
//          attractivePlace.calculate()
          complete("2")
        }
      }
    }~
    pathPrefix("spark"){
      get{
        pathEnd{
          val e = new SparkAnalyze()
//          e.test2()
          complete(new AttractivePlace().calculate("7C:4D:1D:5G:04").toString)
//          complete("!")
        }
      }
    }
  }
}
