package chiselpp

object Main:
  def main(args: Array[String]): Unit =
    val uint1 = UInt(Width(32))
    println(s"uint ${uint1}")
    val uint2 = UInt(32.W)
    println(s"uint ${uint2}")
    val uint3 = 4.U(4.W)
    println(s"uint ${uint3}")

    val wire1 = Wire(UInt(32.W))
    println(s"wire1 ${wire1}")
    val wire2 = Wire(0.U(2.W))
    println(s"wire2 ${wire2}")

    val mod = Module {
      val w1 = Wire(UInt(4.W))
      val w2 = Wire(UInt(2.W))
      val w3 = w1 + w2

      val w4 = Wire(0.U(4.W))
      val w5 = w1 + w4
    }
    println(s"Module ${mod}")
