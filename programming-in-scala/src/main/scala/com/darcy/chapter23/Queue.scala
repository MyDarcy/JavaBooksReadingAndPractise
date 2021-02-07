package com.darcy.chapter23

/**
  * n皇后问题;
  */
object Queue {

  def safe(queue: (Int, Int), queues: List[(Int, Int)]) = queues.forall(placed => valid(queue, placed))

  def valid(queue: (Int, Int), placed: (Int, Int)): Boolean = {
    return queue._1 != placed._1 && // 同行
      queue._2 != placed._2 && // 同列
      (queue._1 - placed._1).abs != (queue._2 - placed._2).abs // 对角线  横纵;
  }

  def queue(n: Int): List[List[(Int, Int)]] = {

    //递归的套路写法;
    def placeQueue(k: Int) :List[List[(Int, Int)]] = {
      if (k == 0) {
        List(List())
      } else {
        for {
          queues <- placeQueue(k - 1) // n-1个皇后放在棋牌上的解
          column <- 1 to n // 皇后可能放的列
          queue = (k, column)
          if safe(queue, queues) // 当前皇后是否和其他皇后冲突;
        } yield queue :: queues // k的皇后时的解
      }
    }
    placeQueue(n)
  }

  def main(args: Array[String]): Unit = {
    val list = queue(8)
    println(list.size)
    list.foreach(println)
  }

}
