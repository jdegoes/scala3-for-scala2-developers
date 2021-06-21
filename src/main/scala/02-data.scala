/**
 * ENUMS
 * 
 * Scala 3 adds support for "enums", which are to sealed traits like case classes 
 * were to classes. That is, enums cut down on the boilerplate required to use 
 * the "sealed trait" pattern for modeling so-called sum types, in a fashion very 
 * similar to how case classes cut down on the boilerplate required to use 
 * classes to model so-called product types.
 * 
 * Strictly speaking, Scala 3 enums are not the same as Java enums: while the 
 * constructors of enums are finite, and defined statically at compile-time in the 
 * same file, these constructors may have parameters, and therefore, the total 
 * number of values of any enum type could be large or infinite.
 * 
 * Enums and case classes provide first-class support for "algebraic data types" 
 * in Scala 3.
 */
package enums: 
  /**
   * EXERCISE 1
   * 
   * Convert this "sealed trait" to an enum.
   */
  sealed trait DayOfWeek
  object DayOfWeek:
    case object Sunday extends DayOfWeek
    case object Monday extends DayOfWeek
    case object Tuesday extends DayOfWeek
    case object Wednesday extends DayOfWeek
    case object Thursday extends DayOfWeek
    case object Friday extends DayOfWeek
    case object Saturday extends DayOfWeek

  /**
   * EXERCISE 2
   * 
   * Augment `DayOfWeek` with Java enum compatibility, by extending `Enum[DayOfWeek]`. Then explore 
   * this interop with Java enums by finding all values of `DayOfWeek`, and by finding the value 
   * corresponding to the string "Sunday".
   */
  def daysOfWeek: Array[DayOfWeek] = ???
  def sunday: DayOfWeek = ???

  /**
   * EXERCISE 3
   * 
   * Convert this "sealed trait" to an enum.
   * 
   * Take special note of the inferred type of any of the case constructors!
   */
  sealed trait Color 
  object Color:
    case object Red extends Color 
    case object Green extends Color 
    case object Blue extends Color
    final case class Custom(red: Int, green: Int, blue: Int) extends Color

  /**
   * EXERCISE 4
   * 
   * Convert this "sealed trait" to an enum.
   * 
   * Take special note of the inferred type parameters in the case constructors!
   */
  sealed trait Result[+Error, +Value]
  object Result:
    final case class Succeed[Value](value: Value) extends Result[Nothing, Value]
    final case class Fail[Error](error: Error) extends Result[Error, Nothing]

  /**
   * EXERCISE 5
   * 
   * Convert this "sealed trait" to an enum.
   * 
   * Take special note of the inferred type parameters in the case constructors!
   */
  sealed trait Workflow[-Input, +Output]
  object Workflow:
    final case class End[Output](value: Output) extends Workflow[Any, Output]

  /**
   * EXERCISE 6
   * 
   * Convert this "sealed trait" to an enum.
   */
  sealed trait Conversion[-From, +To]
  object Conversion:
    case object AnyToString extends Conversion[Any, String]
    case object StringToInt extends Conversion[String, Option[Int]]

/**
 * CASE CLASSES
 * 
 * Scala 3 makes a number of improvements to case classes.
 */
package case_classes:
  /**
   * EXERCISE 1
   * 
   * By making the public constructor private, make a smart constructor for `Email` so that only 
   * valid emails may be created.
   */
  final case class Email(value: String)
  object Email:
    def fromString(v: String): Option[Email] = ???

    def isValidEmail(v: String): Boolean = v.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")

  /**
   * EXERCISE 2
   * 
   * Try to make a copy of an existing `Email` using `Email#copy` and note what happens.
   * 
   */
  def changeEmail(email: Email): Email = ???

  /**
   * EXERCISE 3
   * 
   * Try to create an Email directly by using the generated constructor in the companion object.
   * 
   */
  def caseClassApply(value: String): Email = ???

/**
 * PATTERN MATCHING
 * 
 * Scala 3 provides upgrades to the power and flexibility of pattern matching.
 */  
object pattern_matching:
  /**
   * EXERCISE 1
   * 
   * Remove the unsound usage of `ClassTag` from this pattern match, replacing it with `Typeable`.
   */
  // def getT[T: scala.reflect.ClassTag](list: List[Any]): Option[T] = 
  //   list match 
  //     case (head : T) :: _ => Some(head)
  //     case _ => None 

  val h :: t = ::("foo", Nil)

  /**
   * EXERCISE 2
   * 
   * Scala 3 more strongly checks patterns. Add some more irrefutable patterns to this `for` 
   * comprehension.
   */
  for
    (l, r) <- Some((19, 42))
  yield l + r