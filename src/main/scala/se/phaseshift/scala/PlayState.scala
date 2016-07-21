package se.phaseshift.scala

class PlayState(rows: Int, columns: Int) {
  private var statefunc = (row: Int, column: Int) => { None } : Option[Char]
  private var charfunc  = (opt: Option[Char]) => { opt match { case Some(chr) => chr case None => '?' } } : Char 
  private var playstate = Array.tabulate(rows, columns)(statefunc)

  def print = { 
    for(row <- playstate) {
      println(row.map(charfunc).mkString) 
    }
  }

  def row(rownr: Int, word: String) {
    if(rownr < rows && rownr >= 0) {
      for((chr, index) <- word.zipWithIndex) {
        if(index < columns) playstate(rownr)(index) = Some(chr)
      }
    }
  }

  def row(rownr: Int): String = rownr match {
    case _ if (rownr < rows && rownr >= 0) => playstate(rownr).map(charfunc).mkString
  }

  def column(colnr: Int, word: String) {
    if(colnr < columns && colnr >= 0) {
      for((row, index) <- playstate.zipWithIndex) {
        if (index < word.length) row(colnr) = Some(word(index))
      }
    }
  }

  def column(colnr: Int): String = colnr match {
    case _ if (colnr < columns && colnr > 0) => (playstate.map(_(colnr))).map(charfunc).mkString
  }
}
