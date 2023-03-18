package example

import scala.io.StdIn.readLine

object Charter {

  def main(args: Array[String]): Unit = {

    val pizza = Pizza(List("Pineapple", "Bacon"), 18)
    val pizza2 = Pizza(List("Mushroom", "Sausage"), 12)
    val pizza3 = Pizza(List("Meat", "Tomato"), 12)
    val pizza4 = Pizza(List.empty, 17)

    val order = List(pizza, pizza2, pizza3, pizza4)

    order.foreach(p => println("Price is: " + p.price()))
    val totalPrice = order.map(p => p.price()).sum
    println("Total price: $" + totalPrice)



  }


}
