/**
 * OPTIONAL BRACES
 * 
 * Scala 3 makes braces optional (!). This new Python-esque syntax helps ensure good indentation, 
 * as well as reducing the verbosity and visual noise of Scala code.
 * 
 * Love it or hate it, you're sure to see it in the wild. Take the time to learn the rules of 
 * Scala 3's new optional braces feature!
 */
package optional_braces:
  /**
   * EXERCISE 1
   * 
   * Refactor this class declaration so that it does not utilize curly braces.
   */
  class ClassDecl {
    def run() = println("Hello World!")
  }

  /**
   * EXERCISE 2
   * 
   * Refactor this trait declaration so that it does not utilize curly braces.
   */
  trait TraitDecl {
    def run() = println("Hello World!")
  }

  /**
   * EXERCISE 3
   * 
   * Refactor this object body so that it does not utilize curly braces.
   */
  object ObjectBody {
    def run() = println("Hello World!")
  }

  /**
   * EXERCISE 4
   * 
   * Refactor this anonymous class so that it does not utilize curly braces.
   */
  val anonClass = new TraitDecl {
    def runMore() = println("Goodbye World!")
  }

  /**
   * EXERCISE 5
   * 
   * Refactor this package declaration so that it does not utilize curly braces.
   */
  package package_decl {
    def runEvenMore() = println("See you later!")
  }

  /**
   * EXERCISE 6
   * 
   * Refactor this `if` statement so that it does not utilize curly braces:
   */
  def conditional() = 
    if (2 + 2 != 4) {
      throw new IllegalStateException("The universe is broken")
    }

  /**
   * EXERCISE 7
   * 
   * Refactor this if/else statement so that it does not utilize curly braces.
   */
  def conditional2() = 
    if ("Sherlock Holmes".startsWith("Sher")) {
      println("He is sure!")
    } else {
      println("He is uncertain!")
    }

  /**
   * EXERCISE 8
   * 
   * Refactor this match clause so that it does not utilize curly braces.
   */
  def joke(v: String) = 
    v match {
      case "knock, knock" => println("Who's there?")
      case _ => println("Unknown input!")
    }

  /**
   * EXERCISE 9
   * 
   * Refactor this method body so that it does not utilize curly braces.
   */
  def whatIsYourName = {
    println("What is your name?")
    val name = scala.io.StdIn.readLine()
    println(s"Hello, ${name}!")
  }

  /**
   * EXERCISE 9
   * 
   * Refactor this try/catch so that it does not utilize curly braces.
   */
  def tryItAndCatchIt = 
    try {
      throw new IllegalStateException("Wyoming")
    } catch {
      case _ : IllegalStateException => println("That state is illegal!")
    }
  
  /**
   * EXERCISE 10
   * 
   * Refactor this `for` comprehension so that it does not utilize curly braces.
   */
  def forComprehension =
    val numbers = List(1, 2, 9, 3, -1, 6, 5, 2)

    for {
      number1 <- numbers 
      number2 <- numbers 
      if ((number1 - number2).abs == 2)
    } yield (number1, number2)

  /**
   * EXERCISE 11
   * 
   * Refactor this `while` loop so that it does not utilize curly braces.
   */
  def whileLoop(n: Int) = 
    var i = 0 

    while (i < n) {
      println("All work and no play makes Jack a dull boy")
      i = i + 1
    }

  /**
   * EXERCISE 12
   * 
   * Add an explicit end marker to this braceless class declaration.
   */
  abstract class UserRepository:
    def getUserName(id: String): String
  
  /**
   * EXERCISE 13
   * 
   * Add an explicit end marker to this braceless conditional.
   */
  def conditional3(answer: Int) = 
    if (answer == 42)
      println("The answer to the meaning of life, the universe, and everything.")
  
  /**
   * EXERCISE 14
   * 
   * Add an explicit end marker to this braceless method.
   */
  def tooBigMethod() = 
    println(".")
    println("..")
    println("...")
    println("....")
    println(".....")
    println("......")
    println(".......")
    println("........")

  /**
   * EXERCISE 15
   * 
   * Add an explicit end marker to this braceless try/catch block.
   */
  def tryItAndCatchItAgain = 
    try
      throw new IllegalStateException("Wyoming")
    catch
      case _ : IllegalStateException => println("That state is illegal!")

  /*
   * Optional braces apply to other constructs not yet introduced, including enums, givens, and 
   * extensions! You will learn more about this syntax as these new features are introduced.
   */

/**
 * CONTROL FLOW SYNTAX
 * 
 * Scala 3 adds new syntax for control operators, allowing you to omit parentheses that would have 
 * been required under Scala 2.x syntax. This makes the language more uniform (as parentheses were 
 * not required for guards in conditionals, for example), and modernizes the syntax of the language.
 */
package control_flow:
  /**
   * EXERCISE 1
   * 
   * Refactor this `if` statement to eliminate parentheses using the `then` keyword.
   */
  def conditional(x: Int) = 
    if (x > 0) println("Positive")
    else println("Non-positive")

  /**
   * EXERCISE 2
   * 
   * Refactor this `while` loop to eliminate parentheses using the `do` keyword.
   */
  def repeatN(n: Int)(body: () => Unit): Unit = 
    var i = 0 

    while (i < n) {
      body()
      i = i + 1 
    }

  /**
   * EXERCISE 3
   * 
   * Refactor this `for` comprehension to eliminate the parentheses.
   */
  def forComprehension = 
    val numbers = List(1, 2, 9, 3, -1, 6, 5, 2)

    for (i <- numbers) yield i * i
