package chiselpp

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello world")
    val uint1 = UInt(Width(32))
    println(s"uint ${uint1}")
    val uint2 = UInt(32.W)
    println(s"uint ${uint2}")
    val uint3 = 4.U(4.W)
    println(s"uint ${uint3}")
  }
}
