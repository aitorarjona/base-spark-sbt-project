import org.apache.spark.sql.SparkSession

object HelloWorld {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("HelloWorld")
      .getOrCreate()

    println("Hello, World!")
  }
}
