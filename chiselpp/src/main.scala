package chiselpp

enum HWNode derives CloneType:
  case AddOp(x: Int)
  case Literal

object Main:
  def main(args: Array[String]): Unit =
    val stringnode = summon[CloneType[String]]
    println(stringnode)
    val uintnode = summon[CloneType[HWNode]]
    println(uintnode)
// println("Hello world")
// val uint1 = UInt(Width(32))
// println(s"uint ${uint1}")
// val uint2 = UInt(32.W)
// println(s"uint ${uint2}")
// val uint3 = 4.U(4.W)
// println(s"uint ${uint3}")

// val wire = Wire(UInt(4.W))
// println(s"wire ${wire} ${wire.data}")

// val reg = Reg(UInt(4.W))
// println(s"reg ${reg}")

// val mod = Module {
// val n1 = 0.U(4.W)
// val n2 = 1.U(4.W)
// val n3 = n1 + n2
// val handle = InModuleBody {
// val n1 = 2.U(4.W)
// val n2 = 3.U(4.W)
// val n3 = n1 + n2
// n3
// }
// }
// println(mod)
