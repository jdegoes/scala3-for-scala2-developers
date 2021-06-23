/**
 * TOP-LEVEL DEFINITIONS
 * 
 * Scala 3 allows top-level definitions (variables, methods, and type aliases).
 */
package top_level:
  /**
   * EXERCISE 1
   * 
   * Define a top-level type alias for `List[String]` called `ListString`.
   */
  type ListString 

  /**
   * EXERCISE 2
   * 
   * Define a top-level method called `helloWorld` which prints out "Hello World!".
   */
  def helloWorld = ???

  /**
   * EXERCISE 3
   * 
   * Define a top-level variable called `pi` which is approximately equal to the value of pi.
   */
  lazy val pi = ???

/**
 * TRAIT PARAMETERS
 *
 * Scala 3 introduces trait parameters, which solve a lot of messy initialization order
 * problems in Scala 2.x. 
 */
object trait_parameters:
  trait Console:
    def print(line: String): Unit

  val StandardConsole: Console = println(_)

  /**
   * EXERCISE 1
   * 
   * Remove the field `console`, and instead, introduce a trait parameter.
   */
  trait Logging:
    def console: Console

    def log(line: => String): Unit = console.print(line)

  /**
   * EXERCISE 2
   * 
   * Make the following class extend the trait `Logging`, and pass the trait
   * a value for its `Console` parameter.
   */
  class StandardLogger  

/**
 * EXPLICIT NULLS
 * 
 * When the -Yexplicit-nulls flag is turned on, Scala 3 will treat `Null` as a 
 * subtype of `Any`, and not a supertype of either `AnyRef` or `AnyVal`. Nullable 
 * types are then described with union types.
 */
object explicit_nulls:
  /**
   * EXERCISE 1
   * 
   * Make the following code compile by giving the value a union type that 
   * includes `Null`.
   */
  // val stringOrNull: String = null

  
  /**
   * EXERCISE 2
   * 
   * Test to see if `value` is NOT equal to null, and if it is NOT equal to null, then place the 
   * variable into another local variable that can only be `String`. Then print it out to the 
   * console. Explain your findings.
   */
  def printOutOnlyIfString(value: String | Null): Unit = ???


/**
 * CREATOR APPLICATIONS
 * 
 * Scala 3 introduces creator applications, which is a concise way to create instances 
 * of a class even if it does not have an `apply` method in its companion object.
 */
object creator_applications:
  class Logger(printer: String => Unit) {
    var enabled = true 

    def log(s: => String): Unit = if (enabled) printer(s)
  } 

  /**
   * EXERCISE 1
   * 
   * Simplify the construction of this `Logger` by using creator application.
   */
  val logger = new Logger(println(_))

/**
 * PROXIES
 * 
 * Scala 3 introduces "proxies", otherwise known as _export clauses_. Export 
 * clauses can automatically create forwarders to the members on another object.
 */
object proxies:
  trait Logger:
    def log(line: String): Unit 

  val ConsoleLogger: Logger = println(_)

  /**
   * EXERCISE 1
   * 
   * Make the following `Console` class extend `Logger`, but rather than 
   * implementing the `log` method directly, export it from the `logger` object.
   */
  class Console(logger: Logger):
    def readLine(): String = scala.io.StdIn.readLine()

    def printLine(any: Any): Unit = println(any.toString())
    
/**
 * PARAMETER UNTUPLING
 */
object param_untupling:
  val sum = (x: Int, y: Int) => x + y

  val numbers1 = List(1, 2, 3, 4)
  val numbers2 = List(4, 3, 2, 1)

  /**
   * EXERCISE 1
   * 
   * Map over the "zipped" list of `numbers1` and `numbers2` using the 
   * `sum` function defined above.
   */
  numbers1.zip(numbers2)

/**
 * PROGRAMMATIC STRUCTURAL TYPES
 * 
 * Scala 3 enhances the "extends Dynamic" mechanism to be more powerful 
 * and type safe.
 */
object enhanced_dynamic:
  class ARecord(elems: (String, Any)*) extends Selectable {
    private val fields = elems.toMap
    def selectDynamic(name: String): Any = fields(name)
  }
  type Person = ARecord {
    val name: String
    val age: Int
  }

  val person = ARecord("name" -> "Emma", "age" -> 42).asInstanceOf[Person]

/**
 * OPEN CLASSES
 * 
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

/**
 * INFIX OPERATORS
 * 
 * Scala 3 makes infix syntax "opt-in" to provide a more uniform experience.
 */
object infix:
  final case class Predicate[-A](evaluate: A => Boolean):
    self =>
      def and[A1 <: A](that: Predicate[A1]): Predicate[A1] = 
        Predicate(a1 => self.evaluate(a1) && that.evaluate(a1))

      def or[A1 <: A](that: Predicate[A1]): Predicate[A1] = 
        Predicate(a1 => self.evaluate(a1) || that.evaluate(a1))

      def negate: Predicate[A] = Predicate(a => !evaluate(a))

  def isGreaterThan(n: Int): Predicate[Int] = Predicate(_ > n)
  def isLessThan(n: Int): Predicate[Int] = Predicate(_ < n)
  def isEqualTo(n: Int): Predicate[Int] = Predicate(_ == n)

  /**
   * EXERCISE 1
   * 
   * Make the following code compile by adding the `infix` keyword to the `or` operator.
   */
  // val example = isGreaterThan(12) or isEqualTo(0)

