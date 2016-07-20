package se.phaseshift.scala

class PlayState(rows: Int, columns: Int) {
  private var statefunc = (row: Int, column: Int) => { ((row * columns) + (64 + column)).toChar } : Char
  private var playstate = Array.tabulate(rows, columns)(statefunc)

  def print = { 
    for(row <- playstate) { 
      println('"' + row.mkString + '"') 
    }
  }

  def row(rownr: Int): String = {
    playstate(rownr).mkString
  }

  def column(colnr: Int): String = {
    (playstate.map(_(colnr))).mkString
  }
}
