import scala.io.Source
import scala.util.matching

import java.io.{FileReader, FileNotFoundException, IOException}

object WordStats {
  def main(args: Array[String]) {
    val filename = "./ord.utf8"
    val regex = """ar.b.*""".r

    try {
      val words = Source.fromFile(filename).getLines.toArray

      for(word <- words) {
        word match {
          case regex(_*) => println(word)
          case _ =>
        }
      }
    } catch {
      case ex: FileNotFoundException => println("Couldn't find that file.")
      case ex: IOException => println("Had an IOException trying to read that file" + ex)
    }
  }
}
