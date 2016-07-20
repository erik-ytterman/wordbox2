package se.phaseshift.scala

import scala.io.Source

import java.io.{FileReader, FileNotFoundException, IOException}

object WordStats {
  val regex = """ar.b.*""".r

  def wordFilter(word: String): Boolean = word match {
    case regex(_*) => true
    case "övliga" => true
    case _ => false
  }

  def main(args: Array[String]) {
    val playstate = new PlayState(4,5)

    playstate.print
    println
    println(playstate.row(1))
    println(playstate.column(2))

    try {
      val instream = getClass.getResourceAsStream("/wordlists/Swedish/ord.utf8")
      val words = Source.fromInputStream(instream).getLines.toList
      val filtered = words.filter(wordFilter)
    } catch {
      case ex: IOException => println("Had an IOException trying to read that file" + ex)
    }
  }
}
