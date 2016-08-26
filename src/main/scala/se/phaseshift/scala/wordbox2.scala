package se.phaseshift.scala

import scala.io.Source
import scala.collection.mutable.ListBuffer
import scala.util.Random

import java.io.{FileReader, FileNotFoundException, IOException}

object WordStats {
  // Read words from resource file
  val instream = getClass.getResourceAsStream("/wordlists/Swedish/ord.utf8")
  val words = Source.fromInputStream(instream).getLines.toList
  val gamesize = 4

  // Filter out words of desired length (4)
  val random = new Random(System.currentTimeMillis)
  val valid = words.filter(_.length == gamesize).toList

  // Create list containing the playfield state, one string per pow in the playfield
  var gamestate = List[String]()

  // Anonymouns accessor functions to expose the result
  var rows = () => { gamestate }: List[String]
  var columns = () => { gamestate.transpose.map( _.foldLeft("")(_ + _) ) }: List[String]

  // Recursive solution finding function
  def gamesolver(state: List[String] = List[String]()): Boolean = {

    if(state.length < gamesize) {

      for(word <- random.shuffle(valid)) {
        val newstate = state ::: List(word)

        var columnfilters = newstate.transpose.map( _.foldLeft("")(_ + _) )
        var columnwords = columnfilters.map( columnfilter => valid.filter( _.startsWith(columnfilter) ) )
        var isvalidcolumn = columnwords.map(! _.isEmpty )
        var isvalidword = isvalidcolumn.reduce(_ && _)

        if( isvalidword ) { if( gamesolver(newstate) )  { return true } }
      }

      return false

    } else {
      gamestate = state

      return true
    }
  }

  // Program entry point
  def main(args: Array[String]) {
    try {
      if( gamesolver() ) {
        rows().foreach( println(_) )
      }
      else {
        println("Search failed!")
      }
    } catch {
      case ex: IOException => println("Had an IOException trying to read that file" + ex)
    }
  }
}
