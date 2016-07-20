package se.phaseshift.scala

class PlayState(rows: Int, columns: Int) {
  private var statefunc = (row: Int, column: Int) => { ((row * columns) + (64 + column)).toChar } : Char
  private var playstate = Array.tabulate(rows, columns)(statefunc)

  def print = { 
    for(row <- playstate) { 
      println('"' + row.mkString + '"') 
    }
  }

  def row(rownr: Int, word: String) {
    if(rownr < rows && rownr >= 0) {
      for((chr, index) <- word.zipWithIndex) {
        if(index < columns) playstate(rownr)(index)  = chr
      }
    }
  }

  def row(rownr: Int): String = rownr match {
    case _ if (rownr < rows && rownr >= 0) => playstate(rownr).mkString
  }

  def column(colnr: Int, word: String) {
    if(colnr < columns && colnr >= 0) {
      for((row, index) <- playstate.zipWithIndex) {
        if (index < word.length) row(colnr) = word(index)
      }
    }
  }

  def column(colnr: Int): String = colnr match {
    case _ if (colnr < columns && colnr > 0) => (playstate.map(_(colnr))).mkString
  }
}
