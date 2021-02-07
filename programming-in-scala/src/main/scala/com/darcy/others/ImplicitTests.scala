package com.darcy.others
import Task.online

object ImplicitTests {

  def test(msg: String)(implicit online: Boolean = true) = {
    println(s"[$online]-$msg")
  }

  def main(args: Array[String]): Unit = {
    test("first")
    Task.online = false
    test("second")
    Task.online = true
    test("third")
  }

}
