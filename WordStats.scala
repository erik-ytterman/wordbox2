import scala.io.Source
import java.io.{FileReader, FileNotFoundException, IOException}

object WordStats {
   def main(args: Array[String]) {
      val filename = "./ord.utf8"

      try {
         val words = Source.fromFile(filename).getLines.toArray
 	 words.foreach(println)
      } catch {
         case ex: FileNotFoundException => println("Couldn't find that file.")
         case ex: IOException => println("Had an IOException trying to read that file" + ex)
      }
   }  
}
