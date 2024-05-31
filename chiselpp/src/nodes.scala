package chiselpp
import scala.collection.mutable.ArrayBuffer

// For the IR to be a graph rep, we want the elaborated in memory
// representation of the frontend to be also a graph
sealed abstract class AbstractHWNode:
  val nodes = new ArrayBuffer[AbstractHWNode]()
  def add_node(n: AbstractHWNode): Unit = nodes += n

sealed trait HasType:
  this: AbstractHWNode =>
  def hwtype: HWType

sealed abstract class TypedHWNode extends AbstractHWNode with HasType

sealed abstract class HWOp extends TypedHWNode
sealed class AddOp extends HWOp

sealed abstract trait HasOperation:
  this: TypedHWNode =>

  def + [T <: TypedHWNode](b: T)(implicit builder: Module): TypedHWNode =
    builder.add_node(this)
    builder.add_node(b)

    val addop = new AddOp
    builder.add_node(addop)
    this.add_node(addop)
    b.add_node(addop)

    val output = this.hwtype.max(b.hwtype).cloneType
    builder.add_node(output)
    addop.add_node(output)
    output

sealed class Literal[T <: HWType](val value: Int, val hwtype: HWType)
    extends TypedHWNode with HasOperation:
  override def toString: String = s"Literal ${value} ${hwtype}"

sealed class Wire[T <: HWType](val hwtype: HWType) extends TypedHWNode

object Wire:
  def apply[T <: HWType](hwtype: HWType) = new Wire(hwtype)

sealed class Reg[T <: HWType](val hwtype: HWType) extends TypedHWNode

object Reg:
  def apply[T <: HWType](hwtype: HWType) = new Reg(hwtype)

class Module extends AbstractHWNode:
  override def toString = nodes.map(_.toString).foldLeft("")(_ + "\n" + _)

object Module:
  def apply(init: Module ?=> Unit) =
    given m: Module = new Module()
    init
    m

object InModuleBody:
  def apply(init: Module ?=> AbstractHWNode)(using m: Module) =
    init
