package spark

import com.mongodb.casbah.commons.MongoDBObject
import db.MongoFactory
import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LabeledPoint}
import org.apache.spark.sql.SparkSession

/**
  * Created by bartosz on 08.04.17.
  */
class FavouritePlace(val userId:Int) {

  def getMac():String={
    val sc = SparkConfig.getSc()
    val spark: org.apache.spark.sql.SparkSession = SparkSession.builder().getOrCreate()
    import spark.implicits._
    import com.mongodb.casbah.Imports._
    val dbData = MongoFactory.beaconinfos.find.map(dd => toLP(dd)).toList

    val dataRdd = sc.parallelize(dbData)
    dataRdd.foreach(println)
    val model = NaiveBayes.train(dataRdd, lambda = 1.0, modelType = "multinomial")
    numberToPlace(model.predict(Vectors.dense(userId)).toInt)
  }

  def toLP(obj: MongoDBObject):LabeledPoint = {
    val mac = obj.getAs[String]("mac").get
    val userId = obj.getAs[Long]("userId").get
    LabeledPoint(placeToNumber(mac), Vectors.dense(userId))
  }

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

}
