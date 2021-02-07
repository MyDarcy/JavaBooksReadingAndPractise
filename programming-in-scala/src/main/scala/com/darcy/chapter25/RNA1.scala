package com.darcy.chapter25

final class RNA1 private (val groups: Array[Int], val length: Int) extends IndexedSeq[Base]{
  import RNA1._

  def apply(idx: Int) = {
    if (idx < 0 || idx > length) throw new IndexOutOfBoundsException
    Base.fromInt(groups(idx / N) >> (idx % N * S) & M) // 先把int中某个碱基对一位到最低两位，然后和11&就可以得到;
  }
}

object RNA1 {
  private val S = 2; // 2个bit标识碱基
  private val N = 32 / S // 一个int可以容纳16个碱基
  private val M = (1 << S) - 1 // 3, 即掩码;

  def fromSeq(buf: Seq[Base]): RNA1 = {
    val groups = new Array[Int]((buf.length + N -1) / N) // length个Base需要多少个Int才能存下;
    for(i <- 0 until buf.length) {
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S) // i * N * S 即i Base在一个int中所处的位置; i/N, i%N
    }
    new RNA1(groups, buf.length)
  }

  def apply(bases: Base*) = fromSeq(bases)


  def main(args: Array[String]): Unit = {
    val xs = List(A, G, T, A)
    RNA1.fromSeq(xs)

    val rna1 = RNA1(A, U, G, G, T)

    println(rna1.length)
    println(rna1.last)
    println(rna1.take(3)) // rest4: IndexedSeq[Base] = Vector(A, U, G)

  }
}
