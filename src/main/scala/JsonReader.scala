import org.apache.spark.sql.SparkSession
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization

object JsonReader extends App {
  case class Winemag(id: Option[Int] = None,
                      country: Option[String] = None,
                      points: Option[Int] = None,
                      price: Option[Double] = None,
                      title: Option[String] = None,
                      variety: Option[String] = None,
                      winery: Option[String] = None)
  implicit val formats = Serialization.formats(NoTypeHints)

    val spark = SparkSession.builder().appName("json_reader_novikov").master("local").getOrCreate()
    val sc = spark.sparkContext
    val filename = args(0)

  val winemags = sc.textFile(filename)
      .map(s => parse(s).extract[Winemag])
      .foreach { println }
}
