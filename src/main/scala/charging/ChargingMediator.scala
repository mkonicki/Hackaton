package charging

import com.mongodb.casbah.commons.MongoDBObject
import db.MongoFactory
import json.ChargingStatus

/**
  * Created by bartosz on 07.04.17.
  */
class ChargingMediator(val userId:Long) {

  val VALUE = 15.5
  def createStatus(): ChargingStatus ={
    val size = MongoFactory.beaconinfos.find(MongoDBObject("userId" -> userId)).size
    ChargingStatus(userId, size*VALUE )
  }

  def getMac(obj: MongoDBObject):String={
    obj.getAs[String]("mac").get
  }
}
