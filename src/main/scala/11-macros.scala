import scala.quoted._

/**
 * MACROS
 * 
 * Scala 3 introduces statically-typed, hygienic macros. 
 */
object macro_basics:
  inline def assert(inline expr: Boolean): Unit =
    ${assertImpl('expr)}

  def assertImpl(expr: Expr[Boolean])(using Quotes): Expr[Any] = '{
    val evaluate = $expr

    if (!evaluate) throw new AssertionError(s"Failed assertion: ${${Expr(expr.show)}}")
  }

  // inline def identity1[A](inline expr: A): A = ${'{expr}}
  
  // inline def identity2[A](inline expr: Expr[A])(using Quotes): Expr[A] = '{${expr}}
  
  // inline def identityT1[A](a: A)(using Quotes): ${'[A]} = a

  /**
   * EXERCISE 1
   * 
   * Construct an `Expr[Int]` by using the `Expr.apply` constructor on an int literal.
   */
  def exprInt: Expr[Int] = ???

  /**
   * EXERCISE 2
   * 
   * Construct an `Expr[String]` by using the `Expr.apply` constructor on a string literal.
   */
  def exprString: Expr[String] = ???

  /**
   * EXERCISE 3
   * 
   * Try to construct an `Expr[String]` from a string parameter by using quotation and note your 
   * findings.
   */
  inline def exprString2(inline expr: String)(using Quotes): Expr[String] = ???

  



