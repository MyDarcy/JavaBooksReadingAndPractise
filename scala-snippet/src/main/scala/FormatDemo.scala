object FormatDemo {

  object Constants {
    val ENV_ST = "st"
    val ENV_PROD = "prod"
    val ENV_TEST = "test"
    val HADOOP_1 = "hadoop-1"
    val HADOOP_2 = "hadoop-2"

  }

  def getHadoopUser(env: String): String = {
    env match {
      case Constants.ENV_ST | Constants.ENV_PROD => Constants.HADOOP_1
      case Constants.ENV_TEST => Constants.HADOOP_2
      case _ => throw new RuntimeException(s"env:${env} is not supported.")
    }
  }

  def main(args: Array[String]): Unit = {
    val format = "/user/%s/path/to/data"
    println(format.format("hadoop-123"))
    println(format.format("st"))
    println(format.format("prod"))

    val path = "/user/%s/user/use/mt/result/"
    println(path.format("hadoop-mt"))

    val envSet = Set("prod", "st", "test")
    println(s"envs:${envSet}")

    println(getHadoopUser("prod"))
    println(getHadoopUser("st"))
    println(getHadoopUser("test"))
    println(getHadoopUser("online"))
  }

}
