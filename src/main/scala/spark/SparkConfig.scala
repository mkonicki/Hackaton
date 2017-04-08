package spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by bartosz on 07.04.17.
  */
object SparkConfig {

  def getSc(): SparkContext={
    val props = System.getProperties()
    props.setProperty("spark.driver.allowMultipleContexts","true")
    val conf = new SparkConf().setAppName("DzidaBackend").setMaster("local")
    val sc = new SparkContext(conf)
    sc
  }
}
