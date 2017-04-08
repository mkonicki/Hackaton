package json

/**
  * Created by bartosz on 08.04.17.
  */
final case class StatisticSummary(list:List[Statistic]) {

}

case class Statistic(mac:String, amountOfVisits:Int) {

}
