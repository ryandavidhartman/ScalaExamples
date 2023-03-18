package example

import scala.io.Source
import scala.util.Try
//import scala.io.StdIn.readLine

object Hello extends App {

    def getMovieDataStream: LazyList[Try[MovieRecord]] = {
      val dataUrl = "https://gist.githubusercontent.com/CatTail/18695526bd1adcc21219335f23ea5bea/raw/54045ceeae6a508dec86330c072c43be559c233b/movies.csv"
      val src = Source.fromURL(dataUrl)
      src.getLines().to(LazyList).drop(1).map(MovieRecord.fromString) lazyAppendedAll {
        src.close;
        LazyList.empty
      }
    }

    val (successes, failures) = getMovieDataStream.partition(_.isSuccess)

    val topTen = successes
      .map(_.get)
      .sortWith(_.rating > _.rating)
      .take(10)

    topTen.foreach(println)

  }



