package chiselpp

sealed class Width(val value: Int):
  override def toString: String = s"${value}"

object Width:
  def apply(x: Int): Width = new Width(x)

sealed abstract class HWType

sealed abstract class Bits(val width: Width) extends HWType:
  override def toString: String = s"Bits ${width}"

sealed class UInt(width: Width) extends Bits(width):
  override def toString: String = s"UInt ${width}"

object UInt:
  def apply(width: Width): UInt = new UInt(width)
// def apply(value: Int, width: Width): Literal[UInt] = new Literal(value, new UInt(width))

sealed class Clock extends Bits(Width(1))

object Clock:
  def apply: Clock = new Clock

sealed class Reset extends Bits(Width(1))

object Reset:
  def apply: Reset = new Reset

// Add some syntatic sugar using implicit conversions
extension(value: Int)
  def W: Width = Width(value)
// def U(w: Width) = Literal(value, UInt(w))
