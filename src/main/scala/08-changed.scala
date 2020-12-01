/**
 * VARARG PATTERNS
 * 
 * Scala 3 changes the syntax for vararg patterns.
 */
object varargs:
  final case class Person(name: String, friends: List[Person])

  Person("Sherlock Holmes", Nil) match {
    case Person(name, friends: _*) => 
  }

/**
 * MATCH EXPRESSIONS
 * 
 * Scala 3 changes the syntax for match expressions.
 */
object match_expressions:
  enum PaymentMethod:
    case Amex 
    case Visa
    case MasterCard 

  /**
   * EXERCISE 1
   * 
   * Change the infix match operator into a method call.
   */
  PaymentMethod.Amex match
    case PaymentMethod.Amex => "It's an amex!"
    case _ => "It's something else!"

/**
 * PROGRAMMATIC STRUCTURAL TYPES
 * 
 * Scala 3 enhances the "extends Dynamic" mechanism to be more powerful 
 * and type safe.
 */
object enhanced_dynamic:
  class Record(elems: (String, Any)*) extends Selectable {
    private val fields = elems.toMap
    def selectDynamic(name: String): Any = fields(name)
  }
  type Person = Record {
    val name: String
    val age: Int
  }

  val person = Record("name" -> "Emma", "age" -> 42).asInstanceOf[Person]

/**
 * WILDCARD
 * 
 * Scala 3 changes the syntax of wildcards in types from `_` (which is quite
 * overloaded) to `?`.
 */
object wildcard_types:
  /**
   * EXERCISE 1
   * 
   * Change these wildcard types to use `?` instead of `_`.
   */
  // def serializeMap(map: Map[_ <: String, _ <: String]) = ???

  type Dummy

/**
 * TYPED PATTERNS
 * 
 * Scala 3 more strongly checks patterns.
 */
object typed_patterns:
  val h :: t = ::("foo", Nil)

  /**
   * EXERCISE 1
   * 
   * Add some more irrefutable patterns to this `for` comprehension.
   */
  for
    (l, r) <- Some((19, 42))
  yield l + r

/**
 * Scala 3 introduces an open keyword that is designed for extensible classes.
 */
object open_classes:
  /**
   * EXERCISE 1
   * 
   * Add the keyword `open` to indicate this class is designed for extension.
   */
  class BaseLogger:
    val logger: String => Unit = _ => ()

    def fine(line: String): Unit = logger(s"FINE: ${line}")

  /**
   * EXERCISE 2
   * 
   * Add the keyword `final` to indicate this class is not designed for extension.
   */
  class ConsoleLogger extends BaseLogger:
    override val logger = println(_)