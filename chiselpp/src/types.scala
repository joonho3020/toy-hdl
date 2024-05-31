package chiselpp
import scala.collection.mutable.ArrayBuffer

sealed class Width(val value: Int):
  override def toString: String = s"${value}"

object Width:
  def apply(x: Int): Width = new Width(x)

sealed abstract class HWType

// For the IR to be a graph rep, we want the elaborated in memory
// representation of the frontend to be also a graph
abstract class HWNode

sealed abstract class Bits(val width: Width) extends HWType:
  override def toString: String = s"Bits ${width}"

sealed class UInt(width: Width) extends Bits(width):
  override def toString: String = s"UInt ${width}"

sealed class Literal[T <: HWType](val value: Int, val hwtype: HWType) extends HWNode:
  override def toString: String = s"Literal ${value} ${hwtype}"

  def add(b: HWNode)(implicit builder: Module): HWNode =
    builder.add(this)
    builder.add(b)
    this

object UInt:
  def apply(width: Width): UInt = new UInt(width)
  def apply(value: Int, width: Width): Literal[UInt] = new Literal(value, new UInt(width))

sealed class Clock extends Bits(Width(1))

object Clock:
  def apply: Clock = new Clock

sealed class Reset extends Bits(Width(1))

object Reset:
  def apply: Reset = new Reset

// Add some syntatic sugar using implicit conversions
extension(value: Int)
  def W: Width = Width(value)
  def U(w: Width) = Literal(value, UInt(w))

sealed class Wire[T <: HWType](val data: HWType) extends HWNode

object Wire:
  def apply[T <: HWType](data: HWType) = new Wire(data)

sealed class Reg[T <: HWType](val data: HWType) extends HWNode

object Reg:
  def apply[T <: HWType](data: HWType) = new Reg(data)

class Module:
  val nodes = new ArrayBuffer[HWNode]
  def add(n: HWNode): Unit = nodes += n
  override def toString = nodes.map(_.toString).foldLeft("")(_ + "\n" + _)

def module(init: Module ?=> Unit) =
  given m: Module = new Module()
  init
  m
