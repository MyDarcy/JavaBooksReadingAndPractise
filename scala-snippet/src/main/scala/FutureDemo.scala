import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

/**
 * author:darcy
 * date time:2020/1/2 8:08 下午
 * description:
 *
 */
object FutureDemo {

  def testFuture1() = {
    val fut1 = Future {
      println("enter task1")
      Thread.sleep(2000)
      1 + 1
    }
    val fut2 = Future {
      println("enter task2")
      Thread.sleep(1000)
      2 + 2
    }

    val fut3 = Future {
      println("enter task2")
      Thread.sleep(1000)
      3 + 3
    }

    val ff = fut1.flatMap(v1 => fut2.flatMap(v2 => fut3.map(v3 => v1 + v2 + v3)))
    ff.onComplete{
      case Success(r) => println(r)
      case Failure(r1) => println(r1.getMessage)
    }

    Thread.sleep(5000)


    val result = for {
      x1 <- fut1
      x2 <- fut2
      x3 <- fut3
    } yield x1 + x2 + x3
    val i = Await.result(result, Duration.Inf)
    println(i)
    println()
  }

  def main(args: Array[String]): Unit = {
    // testFuture1()

    testFuture2()
  }

  def testFuture2(): Unit = {
    val fut1 = Future {
      println("enter task1")
      1 + 1
    }
    val fut2 = Future {
      println("enter task2")
      2 + 2
    }

    fut1.flatMap { v1 =>
      fut2.map { v2 =>
        println(s"the result is ${v1 + v2}")
        Thread.sleep(5000)
        v1 + v2
      }
    }.onComplete {
      case Success(r) => {
        println("onComplete")
      }
      case Failure(e) => {
        print(e)
      }
    }

    Thread.sleep(10000)
  }

}
