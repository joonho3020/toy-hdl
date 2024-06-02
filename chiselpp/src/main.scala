package chiselpp

import scala.reflect._

object Main:
  def show[T](value: T)(implicit tag: TypeTag[T]) = tag.toString().replace("chiselpp.TypeLevelProgramming.", "")

  def main(args: Array[String]): Unit = {
    println(show(List(1, 2, 3)))
  }
// def main(args: Array[String]): Unit =
// val uint1 = UInt(Width(32))
// println(s"uint ${uint1}")

// val uint2 = UInt(Width(3))
// println(s"utin2 ${uint2}")

// val uint3 = UInt(3, Width(4))
// println(s"uint3 ${uint3}")
// println(s"uint3 ${uint3.value}")
