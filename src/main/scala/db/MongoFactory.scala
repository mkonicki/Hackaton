package db

/**
  * Created by bartosz on 07.04.17.
  */
import com.mongodb.casbah.MongoConnection

object MongoFactory {
  private val SERVER = "localhost"
  private val PORT   = 27017
  private val DATABASE = "dzida"
  private val COLLECTION = "beaconinfos"
  val connection = MongoConnection(SERVER)
  val collection = connection(DATABASE)(COLLECTION)
}
