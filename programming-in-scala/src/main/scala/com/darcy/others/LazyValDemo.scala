package com.darcy.others

/**
  *
  */
object LazyValDemo {

  def main(args: Array[String]): Unit = {
    Constants.envOption = Some("Java")
    try {
      LazyVal.nameLength
    } catch {
      case e: Error => println("exception access nameLength e:"  + e + "\n")
    }

    println(LazyVal.nameLength)
    println("test")
  }

}
