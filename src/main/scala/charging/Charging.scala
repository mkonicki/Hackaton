package charging

import json.BeaconInfo

/**
  * Created by bartosz on 07.04.17.
  */
class Charging {
  def charge(beaconInfo: BeaconInfo): Unit ={
    val charge = ChargingFactory.create(beaconInfo)
    //TODO continue
  }

}

case class Charge(userId:Long, value:Double)
object ChargingFactory{
  def create(beaconInfo: BeaconInfo):Charge={
    Charge(beaconInfo.userId, 15.5)
  }
}
