object WordStats {
   def integers = List(0,1,1,2,3,4,5,6,7,8,9,10)

   def main(args: Array[String]) {
      val I = 11 :: this.integers

      def evens = I.filter((number: Int) => ((number % 2) == 0)) 
      evens.foreach(println)
   }  
}
