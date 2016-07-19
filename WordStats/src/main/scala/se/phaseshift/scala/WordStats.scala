import scala.io.Source
import scala.util.matching

import java.io.{FileReader, FileNotFoundException, IOException}

object WordStats {
  val regex = """ar.b.*""".r

  def wordFilter(word: String): Boolean = word match {
    case regex(_*) => true
    case "övliga" => true
    case _ => false
  }

  def main(args: Array[String]) {
    val filename = "./ord.utf8"

    try {
      val words = Source.fromFile(filename).getLines.toList
      val filtered = words.filter(wordFilter)
      filtered.foreach(println)
    } catch {
      case ex: FileNotFoundException => println("Couldn't find that file.")
      case ex: IOException => println("Had an IOException trying to read that file" + ex)
    }
  }
}
