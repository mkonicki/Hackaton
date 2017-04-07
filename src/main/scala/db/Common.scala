package db

/**
  * Created by bartosz on 07.04.17.
  */
import com.mongodb.casbah.Imports._
import json.BeaconInfo

case class Stock (symbol: String, price: Double)

object Common {
  /**
    * Convert a Stock object into a BSON format that MongoDb can store.
    */
  def buildMongoDbObject(beaconInfo: BeaconInfo): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "mac" -> "asd"
    builder += "price" -> "asd"
    builder.result
  }
}