package spark

import java.util.Date

import com.mongodb.casbah.commons.MongoDBObject
import db.MongoFactory
import json.{Statistic, StatisticSummary}

/**
  * Created by bartosz on 08.04.17.
  */
class StatisticSummaryFactory {

  def create():StatisticSummary = {
    import com.mongodb.casbah.Imports._
    val dbData = MongoFactory.beaconinfos.toList.map(elem => fromDb(elem))
    StatisticSummary(sumByKeys[String](dbData).map(e => Statistic(e._1, e._2)))
  }

  def sumByKeys[A](tuples: List[(A, Date, Int)]) : List[(A, Int)] = {
    tuples.groupBy(_._1).mapValues(_.map(_._3).sum).toList
  }

  def fromDb(mongoObj:MongoDBObject):Tuple3[String,Date, Int] = {
    val date = mongoObj.getAs[Date]("dateTime").get
    val mac = mongoObj.getAs[String]("mac").get
    (mac,date, 1)
  }
}

