package chiselpp
import scala.collection.mutable.ArrayBuffer

sealed class Width(val value: Int):
  override def toString: String = s"${value}"

object Width:
  def apply(x: Int): Width = new Width(x)

sealed abstract class HW:
  def cloneType: HW

sealed abstract class PrimitiveHW extends HW:
  def cloneType: PrimitiveHW
  def width: Width
  def max(y: PrimitiveHW): PrimitiveHW =
    if (this.width.value >= y.width.value) this.cloneType
    else y.cloneType

sealed trait Instantiable:
  this: HW =>
  val nodes = new ArrayBuffer[HW]()
  def add_node(n: HW): Unit = nodes += n

sealed abstract class StructuralHW extends HW with Instantiable:
  def base_hwtype: HW
  def cloneType: StructuralHW

sealed class Adder[T <: PrimitiveHW](val base_hwtype: T)
    extends StructuralHW with Instantiable:
  def cloneType = new Adder(base_hwtype)

sealed trait Operatable:
  this: StructuralHW =>
  private def get_primitive_type(hw: HW): Option[PrimitiveHW] = hw match
    case x : Literal[_]  => get_primitive_type(x.base_hwtype)
    case x : PrimitiveHW => Some(x)
    case _               => None

  private def assert_primitive_type(hw: Option[PrimitiveHW]): Unit =
    assert(hw.isDefined, "Operation has to be PrimitiveHW, got ${hw}")

  def + [T <: StructuralHW](x: T)(implicit builder: Module): StructuralHW =
    builder.add_node(this)
    builder.add_node(x)

    val type1 = get_primitive_type(this.base_hwtype)
    val type2 = get_primitive_type(x.base_hwtype)
    assert_primitive_type(type1)
    assert_primitive_type(type2)
    val otype = type1.get.max(type2.get)

    val adder = new Adder(otype)
    builder.add_node(adder)
    this.add_node(adder)
    x.add_node(adder)

    val output = Wire(otype.cloneType)
    adder.add_node(output)
    builder.add_node(output)
    output

sealed class UInt(val width: Width) extends PrimitiveHW:
  def cloneType = new UInt(width)
  override def toString: String = s"UInt ${width}"

sealed class Literal[T <: HW](val value: Any, val base_hwtype: T)
    extends StructuralHW with Operatable:
  def cloneType = new Literal(value, base_hwtype)
  override def toString: String = s"Literal ${value} of base_hwtype ${base_hwtype}"

object UInt:
  def apply(width: Width): UInt = new UInt(width)
  def apply(value: Any, width: Width): Literal[UInt] = new Literal(value, UInt(width))

extension(value: Int)
  def W: Width = Width(value)
  def U(w: Width) = Literal(value, UInt(w))

sealed class Wire[T <: HW](val base_hwtype: T)
    extends StructuralHW with Operatable:
  def cloneType = new Wire(base_hwtype)
  override def toString: String = s"Wire of type ${base_hwtype}"

object Wire:
  def apply[T <: HW](hwtype: T) = new Wire[T](hwtype)

class Module extends HW with Instantiable:
  def cloneType: Module = new Module
  override def toString = nodes.map(_.toString).foldLeft("")(_ + "\n" + _)

object Module:
  def apply(init: Module ?=> Unit): Module =
    given m: Module = new Module()
    init
    m
