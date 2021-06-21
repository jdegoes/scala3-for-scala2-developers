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