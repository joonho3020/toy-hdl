package chiselpp

sealed class Width(val value: Int) {
  override def toString: String = s"${value}"
}

object Width {
  def apply(x: Int): Width = new Width(x)
}

sealed abstract class Bits(val width: Width) {
  override def toString: String = s"Bits ${width}"
}

sealed class UInt(width: Width) extends Bits(width) {
  override def toString: String = s"UInt ${width}"
}

sealed class UIntLiteral(val value: Int, width: Width) extends UInt(width) {
  require(value <= ((BigInt(1) << width.value) - 1),
    f"Value ${value} overflows the number of bits ${width.value}")

  override def toString: String = s"UIntLiteral ${value} ${width}"
}

object UInt {
  def apply(width: Width): UInt = new UInt(width)
  def apply(value: Int, width: Width): UIntLiteral = new UIntLiteral(value, width)
}


implicit class IntConversions(value: Int) extends AnyVal {
  def W: Width = Width(value)
  def U(w: Width) = UIntLiteral(value, w)
}
