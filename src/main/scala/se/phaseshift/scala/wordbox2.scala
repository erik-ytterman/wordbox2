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

  // Create helper functions to use gamestate
  val minlength = () => gamestate.isEmpty match { case false => gamestate.map(_.length).min case true => 0 }
  val rows = () => for((row, index) <- gamestate.zipWithIndex.toList) yield { (index + 1, row) }
  val columns = () => for(position <- (1 to minlength()).toList ) yield { (position, gamestate.map(_(position - 1)).mkString) }

  // Define anonymous function that extracts filter strings for each column
  // The result will be a list containing the starting characters for each column
  def columnfilters(word: Option[String]) = {
    (word, gamestate.isEmpty) match {
      case (None, true) => for(a <- (1 to gamesize).toList ) yield { "" }
      case (Some(word), true) => for((chr, index) <- word.zipWithIndex.toList ) yield { chr.toString }
      case (None, false) => for((index, column) <- columns().toList ) yield { column }
      case (Some(word), false) => for((index, column) <- columns().toList ) yield { column + word(index - 1) }
    }
  }

  def columnmatcher(filters: List[String]): List[Boolean] = {
    for(filter <- filters) yield {
      valid.find(_.startsWith(filter)) match {
        case None => false
        case Some(word) => true
      }
    }
  }

  def wordmatcher(word: String): Boolean = {
    return columnmatcher(columnfilters(Some(word))).foldLeft(true)(_ && _)
  }

  def gamereset() = {
    gamestate.clear()
  }

  def gamesolver(depth: Int): Boolean = {
    val possible = valid.filter(wordmatcher(_))

    print(depth)

    if(gamestate.length == gamesize) {
      println("-------------------------------")
      println(rows())
        println(columns())
      println("-------------------------------")
      return(true)
    }
    else if(possible.isEmpty) {
      return(false)
    }
    else {
      for(word <- possible) {

        gamestate += possible(random.nextInt(possible.length))

        if(! gamesolver(depth + 1)) {
          gamestate.remove(gamestate.length - 1)
        }
        else {
          return(true)
        }
      }
    }

    return false
  }

  def main(args: Array[String]) {
    try {
      gamesolver(0)
    } catch {
      case ex: IOException => println("Had an IOException trying to read that file" + ex)
    }
  }
}
