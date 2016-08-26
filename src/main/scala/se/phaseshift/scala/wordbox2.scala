package se.phaseshift.scala

import scala.io.Source
import scala.collection.mutable.ListBuffer
import scala.util.Random

import java.io.{FileReader, FileNotFoundException, IOException}

object WordStats {
  // Read words from resource file
  private val instream = getClass.getResourceAsStream("/wordlists/Swedish/ord.utf8")
  private val words = Source.fromInputStream(instream).getLines.toList.map(_.toUpperCase)
  private var gamesize = 4

  // Filter out words of desired length (4)
  private val random = new Random(System.currentTimeMillis)
  private var valid = words.filter(_.length == gamesize).toList

  // Create list containing the playfield state, one string per pow in the playfield
  private var gamestate = List[String]()
  
  // Recursive solution finding function
  private def gamesolver(state: List[String] = List[String]()): Boolean = {

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

  // Accessor functions to expose the result
  def rows(): List[String] = { gamestate }
  def columns(): List[String] = { gamestate.transpose.map( _.foldLeft("")(_ + _) ) }

  // Program entry point
  def main(args: Array[String]) {
    try {
      if(args.length > 0) {
        gamesize = args(0).toInt
        valid = words.filter(_.length == gamesize).toList.map(_.toUpperCase)
        println( "Using: %d words of length %d".format(valid.length, gamesize) )
      }

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
