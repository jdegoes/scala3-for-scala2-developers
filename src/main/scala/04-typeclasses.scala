/**
 * TYPECLASSES
 * 
 * Scala 3 introduces direct support for typeclasses using contextual features of the language.
 * Typeclasses provide a way to abstract over similar data types, without having to change the 
 * inheritance hierarchy of those data types, providing the power of "mixin" interfaces, but 
 * with additional flexibility that plays well with third-party data types.
 */
object type_classes:
  trait PrettyPrint[-A]:
    extension (a: A) def prettyPrint: String

  given PrettyPrint[String]:
    extension (a: String) def prettyPrint: String = a

  "foo".prettyPrint

  final case class Person(name: String, age: Int)

  /**
   * EXERCISE 1
   * 
   * With the help of the `given` keyword, create an instance of the `PrettyPrint` typeclass for the 
   * data type `Person` that renders the person in a pretty way.
   */
  // given

  /**
   * EXERCISE 2
   * 
   * With the help of the `given` keyword, create a **named* instance of the `PrettyPrint` typeclass 
   * for the data type `Int` that renders the integer in a pretty way.
   */
  // given intPrettyPrint as ...

  /**
   * EXERCISE 3
   * 
   * Using the `summon` function, summon an instance of `PrettyPrint` for `String`.
   */
  val stringPrettyPrint: PrettyPrint[String] = ???

  /**
   * EXERCISE 4
   * 
   * Using the `summon` function, summon an instance of `PrettyPrint` for `Int`.
   */
  val intPrettyPrint: PrettyPrint[Int] = ???

  /**
   * EXERCISE 5
   * 
   * With the help of the `using` keyword, create a method called `prettyPrintIt` that, for any type 
   * `A` for which a `PrettyPrint` instance exists, can both generate a pretty-print string, and 
   * print it out to the console using `println`.
   */
  def prettyPrintIt = ???

  /**
   * EXERCISE 6
   * 
   * With the help of both `given` and `using`, create an instance of the `PrettyPrint` type class
   * for a generic `List[A]`, given an instance of `PrettyPrint` for the type `A`.
   */
  given [A] as PrettyPrint[List[A]]:
    extension (a: List[A]) def prettyPrint: String = ???

  /**
   * EXERCISE 7
   * 
   * With the help of both `given` and `using`, create a **named** instance of the `PrettyPrint` 
   * type class for a generic `Vector[A]`, given an instance of `PrettyPrint` for the type `A`.
   */
  // given vectorPrettyPrint[A] as ...

  import scala.Eql._ 

  /**
   * EXERCISE 8
   * 
   * Using the `derives` clause, derive an instance of the type class `Eql` for 
   * `Color`.
   */
  enum Color:
    case Red 
    case Green 
    case Blue

/**
 * IMPLICIT CONVERSIONS
 * 
 * Scala 3 introduces a new type class called `Conversion` to perform "implicit 
 * conversions"--the act of automatically converting one type to another.
 */
object conversions:
  final case class Rational(n: Int, d: Int)

  /**
   * EXERCISE 1
   * 
   * Create an instance of the type class `Conversion` for the combination of types
   * `Rational` (from) and `Double` (to).
   */
  // given ...
  given Conversion[Rational, Double] = ???

  /**
   * EXERCISE 2
   * 
   * Multiply a rational number by 2.0 (a double) to verify your automatic
   * conversion works as intended.
   */
  Rational(1, 2)