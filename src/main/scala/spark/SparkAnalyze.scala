package spark

/**
  * Created by bartosz on 07.04.17.
  */
class SparkAnalyze {

  def test(): Unit ={
    val sc = SparkConfig.getSc()
    sc.parallelize(List(1,2,3,4)).foreach(e => println(e))
  }
  
}
