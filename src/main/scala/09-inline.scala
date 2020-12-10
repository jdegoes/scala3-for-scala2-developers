import scala.language.implicitConversions

/**
 * INLINE
 * 
 * Scala 3 introduces a powerful new construct called `inline` to inline designated methods at their
 * point of application. In addition to providing a tool to improve performance in hot spots that 
 * could benefit from inlining, `inline` provides a limited form of compile-time evaluation of Scala 
 * code, which is useful for metaprogramming.
 */
object inline_basics:
  /**
   * EXERCISE 1.
   * 
   * Guarantee the Scala compiler will inline the following constant by using the `inline` keyword.
   * Then explicitly ascribe a type and note your findings.
   */
  val LoggingEnabled = false 

  /**
   * EXERCISE 2
   * 
   * Guarantee the Scala compiler will inline the following method anywhere it is applied, by using 
   * the `inline` keyword.
   */
  def log(line: => String): Unit = 
    if (LoggingEnabled) println(line)

  /**
   * EXERCISE 3
   * 
   * Simplify the following expression as the Scala compiler would simplify it.
   */
  val _ = log("This is a test!")

object inline_recursion:
  /**
   * EXERCISE 1
   * 
   * Using recursion, implement the power function, which raises the specified number to the 
   * specified integral power.
   */
  inline def pow(num: Float, exp: Int): Float = ???
  /**
   * EXERCISE 2
   * 
   * Compute the power of 10.5 raised to the power of 3.
   */
  def `10.5 ^ 3` = ???

  /**
   * EXERCISE 3
   * 
   * Try to compute the power of 10.5 raised to the power of 100 and note what happens.
   */
  def `10.5 ^ 100` = ???

object inline_transparent:
  final case class Natural(value: Int)
  object Natural:
     /**
      * EXERCISE 1
      * 
      * Apply the `transparent` modifier to this use of `inline` so the specific subtype of the 
      * return value will be reflected at the application site at compile-time.
      */
    inline def apply(v: Int): Option[Natural] = 
      if (v >= 0) Some(new Natural(v)) else None

    def fromInt(v: Int): Option[Natural] = apply(v)
  end Natural
  
  /**
   * EXERCISE 2
   * 
   * Define an implicit conversion from `Some[Natural]` to `Natural`.
   */
  given Conversion[Some[Natural], Natural] = ???

  /**
   * EXERCISE 3
   * 
   * Change the type of `zero` to `Natural`, and explain why this does or does not compile.
   */
  val zero: Option[Natural] = Natural(0)

object inline_conditional:
  import scala.compiletime._

  final case class Natural(value: Int)
  object Natural:
     /**
      * EXERCISE 1
      * 
      * Apply the `inline` modifier to the conditional to guarantee the branch can be determined at 
      * compile time.
      */
    transparent inline def apply(v: Int): Any = 
      inline if (v >= 0) new Natural(v) else error("Not a natural number")

    def fromInt(v: Int): Option[Natural] = 
      if (v >= 0) Some(new Natural(v)) else None
  end Natural

  /**
   * EXERCISE 2
   * 
   * Create a natural number from an integer literal.
   */
  def natural: Natural = ???

  /**
   * EXERCISE 3
   * 
   * Try to create a natural number from `fortyTwo`.
   */
  def naturalFortyTwo: Natural = ???
  val fortyTwo: Int = 42

object inline_match:
  sealed trait Natural
  case object Zero extends Natural
  final case class Succ[N <: Natural](value: N) extends Natural

  /**
   * EXERCISE 1
   * 
   * Using an inline match and recursion, implement the following function, which converts a Natural
   * number into an integer.
   */
  transparent inline def toInt(n: Natural): Int = ???

  /**
   * EXERCISE 2
   * 
   * Use the `toInt` function to convert `two` into an int. Ascribe it the most precise type you 
   * can.
   */
  final val twoToInt = ???

  def two: Succ[Succ[Zero.type]] = Succ(Succ(Zero))

object compiletime:
  import scala.compiletime._ 
  /**
   * EXERCISE 1
   * 
   * Using the function `constValue`, implement the following `succ` function, which returns the 
   * number after the number having the specified (singleton) type.
   * 
   * Hint: You will have to mark `succ` as inline to call this function.
   */
  def succ[N <: Int]: Int = ???

  /**
   * EXERCISE 2
   * 
   * Call the function `succ` on the type `3`. Make any changes to `succ` that are necessary to 
   * type the result of `succ[3]` as `4`.
   */
  def four = ???

  /**
   * EXERCISE 3
   * 
   * Using the function `constValueOpt`, implement the following `ClassTag` function, which 
   * should return a `Some(n)` if the specified type is a singleton integer, and `None` 
   * otherwise.
   */
  inline def natural[A]: Option[Int] = ???

  /**
   * EXERCISE 4
   * 
   * Using inline match and the type-level function `S[N]`, which returns the successor type of the 
   * singleton type `N`, implement the following function, which "counts" `N`.
   */
  transparent inline def count[N]: Int =
    ??? // inline constValue[N] match

  /**
   * EXERCISE 5
   * 
   * Using `erasedValue` match against the runtime type of a value so you can return a default value
   * of that type.
   */
  inline def defaultValue[A]: Option[A] = ???

  /**
   * EXERCISE 6
   * 
   * Using `error`, produce a compile-time failure if the provided number is less than 0.
   */
  inline def assertNatural(n: Int): Unit = ???

object selective_summon:
  import scala.compiletime.summonFrom

  /**
   * EXERCISE 1
   * 
   * Using `summonFrom`, create a function that makes a set for the provided type: if the type has 
   * an ordering, then use `TreeSet`, otherwise, use `HashSet`.
   */
  inline def setFor[T]: Set[T] = ???

object inline_params:
  inline val LoggingLevel = 1

  /**
   * EXERCISE 1
   * 
   * Use inline parameters to ensure the following has no runtime overhead
   * unless logging is enabled.
   */
  def log(level: Int, line: String): Unit = 
    if (level >= LoggingLevel) println(line)