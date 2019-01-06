package example.types

/**
  * To qualify as a monad, a type has to satisfy three laws"
  *
  * Associativity:
  * (m flatMap f) flatMap g == m flagMap(  (x => f(x) flagMap g) )
  *
  * Left Unit:
  * unit(x) flatMap f == f(x)
  *
  * Right unit:
  * m flatMap unit = m,
  *
  */
trait MyMonad[T] {

  //also called 'bind' in many languages
  def flatMap[U](f:T => MyMonad[U]): MyMonad[U]

  /**
    * There also must be a unit function associated with each monad
    *  Examples:
    *  List is a monad with unit(x) = List(x)
    *  Set is a monad with unit(x) = Set(x)
    *  Option is a monad with unit(x) = Some(x)
    */
  def unit(x:T): MyMonad[T]

  /**
    *  map can be defined in terms of flatMap and unit
    *  m map f == m flatMap ( x  => unit(f(x)))
    *  m map f == m flatMap  (f andThen unit)
    */
}


