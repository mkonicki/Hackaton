package db

/**
  * Created by bartosz on 07.04.17.
  */
import com.mongodb.casbah.Imports._
import json.BeaconInfo


object Common {

  def buildMongoDbObject(beaconInfo: BeaconInfo): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "mac" -> "asd"
    builder += "price" -> "asd"
    builder.result
  }
}