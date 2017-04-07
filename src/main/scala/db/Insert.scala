package db

import com.mongodb.casbah.Imports._
import db.Common.buildMongoDbObject
import json.BeaconInfo

/**
  * Created by bartosz on 07.04.17.
  */
object Insert {

  def save(mongoObject: MongoDBObject) {
    MongoFactory.beaconinfos.save(mongoObject)
  }
}
