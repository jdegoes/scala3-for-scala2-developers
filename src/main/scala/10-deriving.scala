import scala.deriving._
import scala.compiletime.{erasedValue, summonInline}

object typeclass_deriving:
  inline def summonAll[A <: Tuple]: List[Show[?]] = 
    inline erasedValue[A] match
      case _: EmptyTuple => Nil
      case _: (t *: ts) => summonInline[Show[t]] :: summonAll[ts]

  trait Show[A]:
    def show(x: A): String

  object Show:
    given Show[Int]    = _.toString()
    given Show[String] = a => a

    def showElem(elem: Show[?])(x: Any): String =
      elem.asInstanceOf[Show[Any]].show(x)

    def iterator[A](p: A) = p.asInstanceOf[Product].productIterator

    def showSum[A](s: Mirror.SumOf[A], elems: => List[Show[?]]): Show[A] =
      (x: A) => showElem(elems(s.ordinal(x)))(x)

    def showProduct[A](p: Mirror.ProductOf[A], elems: => List[Show[?]]): Show[A] =
      (x: A) => iterator(x).zip(elems.iterator).map {
        case (x, elem) => showElem(elem)(x)
      }.mkString(", ")

    inline given derived[A](using m: Mirror.Of[A]) as Show[A] =
      lazy val elemInstances = summonAll[m.MirroredElemTypes]
      inline m match 
        case s: Mirror.SumOf[A]     => showSum(s, elemInstances)
        case p: Mirror.ProductOf[A] => showProduct(p, elemInstances)
