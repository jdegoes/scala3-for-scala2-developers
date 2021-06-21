/**
 * Scala 3 introduces several new types that increase the power of the Scala type system.
 */

/**
 * INTERSECTION TYPES
 * 
 * Scala 3 introduces intersection types, which are a commutative version of the `with` operator.
 * In Scala 3, `A & B` is the same type as `B & A`. Whereas, `A with B` is only the same as 
 * `B with A` in the event there are no overlaps between `A` and `B`.
 * 
 * Intersection types are useful to describe types having all the members of other types.
 * 
 * Commutativity: A & B == B & A
 * Associativity: (A & B) & C == A & (B & C)
 * A & Nothing == Nothing
 * Distributivity: A & (B | C) == A & B | A & C
 */
object intersection_types:
  final case class User(name: String, id: String, email: String)
  
  trait HasLogging:
    def logging: Logging

  final case class Logging(log: String => Unit)
  val TestLogging = Logging(println(_))

  trait HasUserRepo:
    def userRepo: UserRepo

  final case class UserRepo(getUserById: String => User)
  val TestUserRepo = UserRepo(_ => User("Sherlock Holmes", "sholmes", "sherlock@holmes.com"))

  /**
   * EXERCISE 1
   * 
   * Form the intersection of the types `HasLogging` and `HasUserRepo` by using the type 
   * intersection operator `&`.
   */
  type HasLoggingAndUserRepo

  /**
   * EXERCISE 2
   * 
   * Using the `IsEqual` helper method, test to see if the type `HasLogging & HasUserRepo` is the 
   * same as the type `HasUserRepo & HasLogging`.
   */
  // IsEqual ...

  def IsEqual[A, B](using ev: A =:= B) = ()

  /**
   * EXERCISE 3
   * 
   * To create a class with a given intersection type, the `with` operator may be used.
   * 
   * Create class that has the type `HasUserRepo & HasLogging`.
   */
  class BothUserRepoAndLogging

/**
 * UNION TYPES
 * 
 * Scala 3 introduces union types, which have no direct analogue in Scala 2.x. The union of two 
 * types `A` and `B`, written `A | B`, describes the type of values that have either type `A` or 
 * type `B`. For example, `Int | String` is the type of values that have either type `Int` or 
 * type `String`. Union types are powerful but do have limitations stemming from type erasure.
 * 
 * Commutativity: A | B == B | A
 * Associativity: A | (B | C) == (A | B) | C
 * Identity:      A | Nothing == A 
 * forall B >: A: A | B == B
 */
object union_types:
  final case class PaymentDenied(message: String)
  final case class MissingAddress(message: String)
  final case class NetworkError(message: String)

  /**
   * EXERCISE 1
   * 
   * Form the union of the types `PaymentDenied` and `MissingAddress` using the type union 
   * operator `|`.
   */
  type PaymentDeniedOrMissingAddress = PaymentDenied | MissingAddress

  /**
   * EXERCISE 2
   * 
   * Create a value of type `PaymentDeniedOrMissingAddress` by assigning the following variable to 
   * a `PaymentDenied` error.
   */
  val example1: PaymentDeniedOrMissingAddress = ???

  /**
   * EXERCISE 3
   * 
   * Create a value of type `PaymentDeniedOrMissingAddress` by assigning the following variable to 
   * a `MissingAddress` error.
   */
  val example2: PaymentDeniedOrMissingAddress = ???

  /**
   * EXERCISE 4
   * 
   * Perform a pattern match on `example2`, covering each possibility and printing out the 
   * error messages to the console.
   */
  // example2 match 

  /**
   * EXERCISE 5
   * 
   * Try to pattern match on `SomeList` and handle both cases. Explain 
   * your findings and what this implies about union types.
   */
  def whatList(l: SomeList) = ???

  type SomeList = List[String] | List[Int]

/**
 * MATCH TYPES
 * 
 * Match types bring the `match` construct to the type level, allowing the creation of type-level 
 * functions that return different types depending on the (statically known) input types.
 */
object match_types:
  type Combine[Left, Right] = Left match
    case Unit => Right 
    case ? => 
      Right match
        case Unit => Left 
        case ? => (Left, Right)

  /**
   * EXERCISE 1
   * 
   * Construct a value of the appropriate type, which is computed using the match type `Combine`.
   */
  val unitAndString: Combine[Unit, String] = ???

  /**
   * EXERCISE 2
   * 
   * Construct a value of the appropriate type, which is computed using the match type `Combine`.
   */
  val stringAndUnit: Combine[String, Unit] = ???

  /**
   * EXERCISE 3
   * 
   * Construct a value of the appropriate type, which is computed using the match type `Combine`.
   */
  val stringAndString: Combine[String, String] = ???

  /**
   * EXERCISE 4
   * 
   * On the JVM, collection types generally "box" primitive values like `Int`, creating wrapper
   * values around these primitives. One of the exceptions is `Array`, which does not box primitive 
   * values.
   * 
   * Create a match type that will return Scala's `Vector` for all types except primitive types,
   * but for primitive types, will return Scala's `Array`.
   */
  type Collection[X]

  /**
   * EXERCISE 5
   * 
   * Match types can be recursive. Write a match type that determines the "atom" type of a string 
   * or array or iterable.
   */
  type ElementType[X] = X match
    case String => Char
    case Array[t] => ElementType[t]
    case Iterable[t] => ElementType[t]
    case AnyVal => X

  /**
   * EXERCISE 6
   * 
   * Match types can be used to define dependently typed methods. Implement the following 
   * `head` function which returns the head of the specified value (a character of a string, 
   * or the first element of an array or iterable, or the passed in value, otherwise).
   */
  def headOf[X](x: X): ElementType[X] = ???

  /**
   * EXERCISE 7
   * 
   * Match types don't have to define "total" functions. Try to construct a value of the type 
   * `Partial[Int]`.
   */
  def partialInt: Partial[Int] = ???
  type Partial[X] = 
    X match
      case String => Float 
      case Float => String

/**
 * OPAQUE TYPES
 * 
 * Opaque types are a new variant of a type alias that hides information on the underlying type.
 * This can be useful to create novel types that are backed by other types, without any runtime 
 * overhead. An example might be an "Email" type that is really backed by a "String", but which is 
 * treated as a unique (opaque) type by the Scala compiler.
 */
object opaque_types:
  object email_example:
    opaque type Email = String
    object Email:
      /**
       * EXERCISE 1
       * 
       * The scope of an opaque type has special privileges. Create a constructor for email that
       * takes a string, and returns an `Email`.
       */
      def apply() = ???
    end Email

    /**
     * EXERCISE 2
     * 
     * Define an extension method to retrieve the username of an email (the part before the '@' 
     * character).
     */
    extension (e: Email) def username: String = ???
  end email_example

  import email_example.*

  /**
   * EXERCISE 3
   * 
   * Use the constructor you made to build an `Email` value given a `String`.
   */
  lazy val exampleEmail: Email = ???

  /**
   * EXERCISE 4
   * 
   * Try to pass the email you constructed to the function `printString` and note your findings.
   */
  printString(???)

  def printString(string: String): Unit = println(string)

  object natural_example:
    /**
     * EXERCISE 5
     * 
     * Add a subtype bound to `Natural` (on the left-hand side of the equals sign). This subtype 
     * relationship must be true and it will be "exported" outside the scope in which the opaque
     * type is defined.
     */
    opaque type Natural = Int

    object Natural:
      /**
       * EXERCISE 6
       * 
       * Define a smart constructor that, given an `Int`, may or may not return a `Natural`, 
       * depending on whether the number is a natural number (non-negative) or not.
       */
      def fromInt(i: Int): Option[Natural] = ???
    end Natural
  end natural_example

  import natural_example.*

  /**
   * EXERCISE 7
   * 
   * Construct an example natural number from the number 5, and call `get` on the `Option` because
   * you know it is a natural number.
   */
  lazy val exampleNatural: Natural = ???

  /**
   * EXERCISE 8
   * 
   * Try to pass the natural number to the function `printInt` and note your findings.
   */
  printInt(???)

  def printInt(v: Int): Unit = println(v.toString())

/**
 * POLYMORPHIC FUNCTION TYPES
 * 
 * Scala 3 introduces polymorphic function types, which gives functions the ability to be 
 * parametrically polymorphic. In Scala 2.x, only methods may be parametrically polymorphic.
 */
object polymorphic_functions:
  def identityMethod[A](a: A): A = a 
  val identityFn: [X] => X => X = [A] => (a: A) => a

  /**
   * EXERCISE 1
   * 
   * Define a polymorphic function `firstFn` that does exactly what the method `firstMethod` does.
   */
  lazy val firstFn = ???
  def firstMethod[A, B](tuple: (A, B)): A = tuple._1

  /**
   * EXERCISE 2
   * 
   * Define a polymorphic function `secondFn` that does exactly what the method `secondMethod` does.
   */
  lazy val secondFn = ???
  def secondMethod[A, B](tuple: (A, B)): B = tuple._2

/**
 * DEPENDENT FUNCTION TYPES
 * 
 * Scala 3 introduces dependent function types, which give function types the ability to model 
 * path-dependent functions that were previously only possible using methods.
 */
object dependent_functions:
  trait Entry:
    type Out

  def getMethod(entry: Entry): entry.Out = ???

  /**
   * EXERCISE 1
   * 
   * Explicitly provide a type signature for `getFn`.
   */
  lazy val getFn = (e: Entry) => getMethod(e)

  trait Combine[L, R]:
    type Out

    def combine[L, R](l: L, r: R): Out

  /**
   * EXERCISE 2
   * 
   * Define a polymorphic function `combineFn` that does exactly what the method 
   * `combineMethod` does.
   */
  lazy val combineFn = ???
  def combineMethod[L, R](l: L, r: R, c: Combine[L, R]): c.Out = c.combine(l, r)

/**
 * Scala 3 introduces first-class support for "type lambdas", which previously had to 
 * be emulated using structural types and type projection, and gave rise to the popular 
 * "kind-projector" plug-in as a way of simplifying their expression.
 */
object type_lambdas:
  type MapK[K] = [V] =>> Map[K, V]

  type MapString[V] = MapK[String][V]

  trait Sizable[F[_]]:
    def size[A](fa: F[A]): Int

  val sizableList = new Sizable[List]:
    def size[A](fa: List[A]): Int = fa.length
  /**
   * EXERCISE 1
   * 
   * Define a `Sizable` for `Map` for the given key type `K`. You will have to 
   * use a type lambda.
   */
  def sizableMap[K] = ???

  /**
   * EXERCISE 2
   * 
   * Define a type-level function `Flip` that accepts a type constructor (`[*, *] => *`), and 
   * returns another type constructor that merely flips the order of type parameters to the first 
   * type constructor.
   */
  type Flip[F[_, _]]

  /**
   * EXERCISE 3
   * 
   * Use the `Flip` type constructor you defined to flip the order of type parameters to `Map`.
   */
  type FlippedMap[K, V]

  /**
   * EXERCISE 4
   * 
   * Define a type-level function `Curry` that accepts a type constructor (`[*, *] => *`), and 
   * returns another type constructor that takes one type parameter, and returns another type 
   * constructor which takes one type parameter, returning the type constructed by the original 
   * type constructor, fully applied with both type parameters.
   */
  type Curry[F[_, _]] = [A] =>> [B] =>> F[A, B]

  // type Uncurry[F[_][_]] = [A, B] =>> F[A][B]

  /**
   * EXERCISE 5
   * 
   * When `-Ykind-projector` is specified, Dotty will emulate kind-projector 
   * syntax. Partially apply `Map` to the key type parameter with `K`, using the 
   * placeholder `*` for the value type parameter.
   */
  // def sizableMap2[K] = sizableMap[K]


/**
 * CONTEXT FUNCTIONS
 * 
 * Scala 3 introduces context functions, which are functions that depend on some context.
 */
object context_functions:
  trait Program:
    def addOp(op: Op): Unit 
  object Program:
    def make(): Program = 
      var ops = List.empty[Op]
      new Program:
        def addOp(op: Op): Unit = 
          ops = op :: ops


  def addOp(op: Op)(using p: Program) = 
    p.addOp(op)

  enum Op:
    case PushInt(v: Int)
    case Pop
    case Mul 
    case Sub
    case Add

  def op(o: Op): Program ?=> Unit = addOp(o)

  def pushInt(i: Int): Program ?=> Unit = op(Op.PushInt(i))
  val mul: Program ?=> Unit = op(Op.Mul)

  def program[A](f: Program ?=> A): A = 
    given Program = Program.make()
    f 

  program {
    pushInt(12)
    pushInt(23)
    mul
  }

  /**
   * EXERCISE 1
   * 
   * Define a small DSL for building HTML.
   */
  def p: HTML[Unit] = ???

  type HTML[+A] = StringBuilder ?=> A


/**
 * SINGLETON TYPES
 * 
 * Literals in Scala now have their own singleton types, which are subtypes of their broader types.
 * For example, the value `true` has a subtype of `Boolean`, namely, `true`. Singleton types
 * provide additional precision and are a relatively simple change to the language that is useful 
 * in conjunction with type-level and metaprogramming.
 */
object singleton_types:
  /**
   * EXERCISE 1
   * 
   * Explicitly ascribe this literal value a singleton type.
   */
  val trueValue = true

  /**
   * EXERCISE 2
   * 
   * Test to see if `true` is a subtype of `Boolean` by using the helper type function 
   * `IsSubtypeOf`.
   */
  type trueSubtypeBoolean

  /**
   * EXERCISE 3
   * 
   * Explicitly ascribe this literal value a singleton type.
   */
  val stringValue = "name"

  /**
   * EXERCISE 4
   * 
   * Explicitly ascribe this literal value a singleton type.
   */
  val floatValue = 3.1415f  

  infix type IsSubtypeOf[A, B >: A]