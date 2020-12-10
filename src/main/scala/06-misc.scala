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
