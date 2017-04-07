package db

import java.text.SimpleDateFormat
import java.util.Date

import com.mongodb.casbah.Imports._
import json.BeaconInfo

/**
  * Created by bartosz on 07.04.17.
  */
class BeaconInfoFacade {

  def save(beaconInfo: BeaconInfo): Unit ={
    Insert.save(createMongoObject(beaconInfo))
  }


  private def createMongoObject(beaconInfo: BeaconInfo):MongoDBObject={
    val builder = MongoDBObject.newBuilder
    builder += "mac" -> beaconInfo.mac
    builder += "dateTime" -> BeaconInfoFacade.createDate(beaconInfo.date, beaconInfo.time)
    builder += "userId" -> beaconInfo.userId
    builder.result
  }


}
object BeaconInfoFacade{
  def createDate(date:String, time:String):Date = {
    val dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    dateFormatter.parse(date+" "+time)
  }
}