package se.phaseshift.scala

import scala.io.Source
import scala.collection.mutable.ListBuffer
import scala.util.Random

import java.io.{FileReader, FileNotFoundException, IOException}

object WordStats {
  // Read words from file and filter according to game size
  val instream = getClass.getResourceAsStream("/wordlists/Swedish/ord.utf8")
  val words = Source.fromInputStream(instream).getLines.toList
  val gamesize = 5
  val valid = words.filter(_.length == gamesize)

  // Create random generator
  val random = new Random(System.currentTimeMillis)

  // Create list containing the playfield state, one string per pow in the playfield
  val gamestate = ListBuffer[String]()

  def main(args: Array[String]) {
    try {
      for((word, index) <- valid.zipWithIndex) {
        println((index,word))
      }
    } catch {
      case ex: IOException => println("Had an IOException trying to read that file" + ex)
    }
  }
}
