package example

import scala.util.Try

case class MovieRecord(name: String, runningTime: Double, rating: Double)

object MovieRecord {
  def fromString(s: String): Try[MovieRecord] = Try {
    val dataArray: Array[String] = s.split(",").map(_.trim)
    MovieRecord(name = dataArray(0), runningTime = dataArray(1).toDouble, rating = dataArray(2).toDouble)
  }
}