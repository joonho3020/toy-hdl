package chiselpp
import scala.collection.mutable.ArrayBuffer

// For the IR to be a graph rep, we want the elaborated in memory
// representation of the frontend to be also a graph
// sealed abstract class HWNode:
// val nodes = new ArrayBuffer[HWNode]()
// def add_node(n: HWNode): Unit = nodes += n
// def cloneType: HWNode

// sealed abstract class HWOp extends HWNode

// sealed class AddOp extends HWOp:
// def cloneType: AddOp = new AddOp

// sealed abstract trait HasOperation:
// this: HWNode =>

// def + [T <: HWNode](b: T)(implicit builder: Module): HWNode =
// builder.add_node(this)
// builder.add_node(b)

// val addop = new AddOp
// builder.add_node(addop)
// this.add_node(addop)
// b.add_node(addop)

// val output = b.cloneType
// builder.add_node(output)
// addop.add_node(output)
// output

// sealed class Literal[T <: HWType](val value: Int, val hwtype: HWType)
// extends HWNode with HasOperation:
// def cloneType: Literal[T] = new Literal(value, hwtype)
// override def toString: String = s"Literal ${value} ${hwtype}"

// sealed class Wire[T <: HWType](val data: HWType) extends HWNode:
// def cloneType: Wire[T] = new Wire(data)

// object Wire:
// def apply[T <: HWType](data: HWType) = new Wire(data)

// sealed class Reg[T <: HWType](val data: HWType) extends HWNode:
// def cloneType: Reg[T] = new Reg(data)

// object Reg:
// def apply[T <: HWType](data: HWType) = new Reg(data)

// class Module extends HWNode:
// def cloneType: Module = new Module
// override def toString = nodes.map(_.toString).foldLeft("")(_ + "\n" + _)

// object Module:
// def apply(init: Module ?=> Unit) =
// given m: Module = new Module()
// init
// m

// object InModuleBody:
// def apply(init: Module ?=> HWNode)(using m: Module) =
// init
