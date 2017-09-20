package com.reflection;

public class ReflectionBean {
  public String publicTest;
  private static String privateTest;
  public int age = 99;


  public String getPublicTest() {
    return publicTest;
  }

  public void setPublicTest(String publicTest) {
    this.publicTest = publicTest;
  }

  public void setPublicTest(String publicTest, String test) {
    this.publicTest = publicTest;
  }

  public static String getPrivateTest() {
    return privateTest;
  }

  public static void setPrivateTest(String privateTest) {
    ReflectionBean.privateTest = privateTest;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }


}
