package chiselpp
import scala.collection.mutable.ArrayBuffer
import scala.quoted._

sealed class Width(val value: Int):
  override def toString: String = s"${value}"

object Width:
  def apply(x: Int): Width = new Width(x)

sealed abstract class HW

sealed class Literal[V, T <: HasLiteral[V]](val hwtype: T)(val value: V) extends HW

sealed trait HasLiteral[T <: Any]

sealed class UInt(val width: Width) extends HW with HasLiteral[BigInt]:
  override def toString: String = s"UInt ${width}"

object UInt:
  def apply(w: Width): UInt = new UInt(w)
  def apply(value: BigInt, w: Width) = new Literal(new UInt(w))(value)
