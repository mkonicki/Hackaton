package spark
import java.util.Date

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.VectorIndexer
import org.apache.spark.ml.regression.{AFTSurvivalRegression, RandomForestRegressor}
import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.regression.LinearRegressionModel
import org.apache.spark.mllib.regression.LinearRegressionWithSGD
import org.apache.spark.sql.SparkSession

/**
  * Created by bartosz on 07.04.17.
  */
class SparkAnalyze {

  def test(): Unit ={

    val sc = SparkConfig.getSc()
    val spark: org.apache.spark.sql.SparkSession = SparkSession.builder().getOrCreate()
    import spark.implicits._
    val training = sc.parallelize(List(1,2,3,4))
    val trainDs = training.toDS()

    import org.apache.spark.ml.feature.Word2Vec
    import org.apache.spark.ml.linalg.Vector
    import org.apache.spark.sql.Row

    // Input data: Each row is a bag of words from a sentence or document.
    val documentDF = spark.createDataFrame(Seq(
      "Hi I heard about Spark".split(" "),
      "I wish Java could use case classes".split(" "),
      "Logistic regression models are neat".split(" ")
    ).map(Tuple1.apply)).toDF("text")

    // Learn a mapping from words to Vectors.
    val word2Vec = new Word2Vec()
      .setInputCol("text")
      .setOutputCol("result")
      .setVectorSize(3)
      .setMinCount(0)
    val model = word2Vec.fit(documentDF)

    val result = model.transform(documentDF)
    result.collect().foreach { case Row(text: Seq[_], features: Vector) =>
      println(s"Text: [${text.mkString(", ")}] => \nVector: $features\n") }
  }

  def test2(): Unit ={

    val sc = SparkConfig.getSc()
    val spark: org.apache.spark.sql.SparkSession = SparkSession.builder().getOrCreate()
    import spark.implicits._
    val data = List(LabeledPoint(1,Vectors.dense(new Date("12/01/2012").getTime)),LabeledPoint(2,Vectors.dense(20)),LabeledPoint(1,Vectors.dense(12)),LabeledPoint(2,Vectors.dense(23)))

    val dataRdd = sc.parallelize(data)
    val numIterations = 100
    val stepSize = 0.001

    val quantileProbabilities = Array(0.3, 0.6)
    val aft = new AFTSurvivalRegression()
      .setQuantileProbabilities(quantileProbabilities)
      .setQuantilesCol("quantiles")

    val test = sc.parallelize(List(20))
    val predictedValue = aft.fit(test.toDS())
    println("sadasdasdA "+aft.fit(test.toDS()))
  predictedValue
  }

}
