package me.darcy.utils;

import java.util.stream.IntStream;

public class PrintUtils {

  public static void printlnDash() {
    printlnDash(80);
  }

  public static void printlnDash(int count) {
    IntStream.range(0, count).boxed().forEach(i -> System.out.print("*"));
    System.out.println();
  }

  public static void println(String string) {
    System.out.println(string);
  }

  public static void printlnDash(String str) {
    System.out.println(str);
    printlnDash(80);
  }

}
