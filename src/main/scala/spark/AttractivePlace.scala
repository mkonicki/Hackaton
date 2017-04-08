package spark

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import com.mongodb.casbah.commons.MongoDBObject
import db.MongoFactory
import json.AttractivePlace
import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionWithSGD}
import org.apache.spark.sql.SparkSession

/**
  * Created by bartosz on 08.04.17.
  */
class AttractivePlace {

  val DATE_DIV_NUMBER = 100000

  def getAttractivePlaceQueue(id:Int): json.AttractivePlace ={
    AttractivePlace(numberToPlace(id), calculate(numberToPlace(id)))
  }

  def calculate(mac:String):List[Int]={
    val sc = SparkConfig.getSc()
    val spark: org.apache.spark.sql.SparkSession = SparkSession.builder().getOrCreate()
    import spark.implicits._
    import com.mongodb.casbah.Imports._
    val dbData = MongoFactory.beaconinfos.find.map(dd => toLP(dd)).toList.filter(dd => dd.label == placeToNumber(mac))

    val dataRdd = sc.parallelize(dbData)
    dataRdd.foreach(println)
    val numIterations = 100
    val stepSize = 0.000001
    val model = LinearRegressionWithSGD.train(dataRdd, numIterations, stepSize)
    //val date = dateToNumber(new Date())
    val date = Vectors.dense(1715)
    List(verifyEstimatedQueue(model.predict(Vectors.dense(815)).toInt), verifyEstimatedQueue(model.predict(Vectors.dense(1215)).toInt), verifyEstimatedQueue(model.predict(Vectors.dense(1715)).toInt))
  }
  def toLP(obj: MongoDBObject):LabeledPoint = {
    val mac = obj.getAs[String]("mac").get
    val date = obj.getAs[Date]("dateTime").get
    LabeledPoint(placeToNumber(mac), dateToNumber(date))
  }


  def verifyEstimatedQueue = (value:Int) => if(value<1) 0 else (Math.sqrt(value)* 63).toInt

  val placeToNumber = (mac:String) => mac match{
    case "12312dasda:asdas" => 5
    case "7C:4D:1D:5G:04" => 4
    case "7C:4D:1D:5G:03" => 3
    case "7C:4D:1D:5G:02" => 2
    case "7C:4D:1D:5G:01" => 1
  }

  val numberToPlace = (id:Int) => id match{
    case 1 => "7C:4D:1D:5G:01"
    case 2 => "7C:4D:1D:5G:02"
    case 3 => "7C:4D:1D:5G:03"
    case 4 => "7C:4D:1D:5G:04"
    case 5 => "12312dasda:asdas"
  }
  val dateToNumber = (date:Date) =>{
    val localTimeFormat = new SimpleDateFormat("HH:mm")
    val time = localTimeFormat.format(date).replaceAll(":","")

    Vectors.dense(time.toInt)
  }

  val stringToDate = (date:String) => {
    val formatter = new SimpleDateFormat("yyyy/mm/dd ")
  }
}
