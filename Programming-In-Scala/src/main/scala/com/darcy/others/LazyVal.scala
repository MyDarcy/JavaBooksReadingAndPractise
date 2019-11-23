package com.darcy.others

object LazyVal {

  lazy val name: String = if (Constants.envOption.get == "Java") "System.out.println" else if (Constants.envOption.get == "Scala") "println" else "echo"
  val nameLength = name.size


}
