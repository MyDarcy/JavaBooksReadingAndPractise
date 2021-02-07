import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success}

val a:Future[String] = Future{
  println("a开始",System.currentTimeMillis())
  Thread.sleep(2000)
  println("a结束",System.currentTimeMillis())
  "hello world!"}

println("I am working start",System.currentTimeMillis())
Thread.sleep(2000)
println("I am working end",System.currentTimeMillis())

a.onComplete {
  case Success(r) => {
    println("回调",System.currentTimeMillis())
    println(s"the result is ${r}")}
  case Failure(t) => println(s"some Exception:${t.getMessage}")
}
Thread.sleep(2000)

a.flatMap(r => a.flatMap())