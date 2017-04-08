package json

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

/**
  * Created by bartosz on 07.04.17.
  */
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val beaconInfoFormat = jsonFormat4(BeaconInfo)
  implicit val chargingStatusFormat = jsonFormat2(ChargingStatus)
  implicit val attractivePlaceFormat = jsonFormat2(AttractivePlace)
}
