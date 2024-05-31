package chiselpp

sealed class Width(val value: Int):
  override def toString: String = s"${value}"
  def max(w: Width): Width = if (this.value >= w.value) this else w

object Width:
  def apply(x: Int): Width = new Width(x)
  def max(x: Width, y: Width): Width = x.max(y)

sealed abstract class HWType:
  def cloneType: HWType
  def width: Width
  def max(b: HWType): HWType =
    if (this.width.value >= b.width.value) this else b

sealed abstract class Bits(val width: Width) extends HWType:
  override def toString: String = s"Bits ${width}"
  def cloneType: Bits = new Bits(width)

sealed class UInt(width: Width) extends Bits(width):
  override def toString: String = s"UInt ${width}"
  def cloneType: UInt = new UInt(width)

object UInt:
  def apply(width: Width): UInt = new UInt(width)
  def apply(value: Int, width: Width): Literal[UInt] = new Literal(value, new UInt(width))

sealed class Clock extends Bits(Width(1)):
  def cloneType: Clock = new Clock

object Clock:
  def apply: Clock = new Clock

sealed class Reset extends Bits(Width(1)):
  def cloneType: Reset = new Reset

object Reset:
  def apply: Reset = new Reset

// Add some syntatic sugar using implicit conversions
extension(value: Int)
  def W: Width = Width(value)
  def U(w: Width) = Literal(value, UInt(w))
