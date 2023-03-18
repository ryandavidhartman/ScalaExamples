package example




case class Pizza(toppings: Seq[String], size: Int) {

  def price(): Double = {
    toppings.length * 0.25 + size.toDouble
  }
}
