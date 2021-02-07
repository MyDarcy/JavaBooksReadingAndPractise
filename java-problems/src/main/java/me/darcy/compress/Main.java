package me.darcy.compress;


import com.google.common.collect.Lists;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Random;

/**
 * https://my.oschina.net/OutOfMemory/blog/805427
 */
public class Main {

  public static void writeRandomData() {
    Random random = new Random();
    String fileName = "data.dat";
    fileName = new File(".").getAbsolutePath() + "/JavaProblems/docs/" + fileName;
    System.out.println(fileName);

    try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));) {
      for (int i = 0; i < (int) Character.MAX_VALUE * 4; i++) {
        outputStream.write(random.nextInt((int) Character.MAX_VALUE));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(new File(fileName).length());

  }

  public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//    writeRandomData();

    String fileName = new File(".").getAbsolutePath() + "/JavaProblems/docs/data.dat";

    FileInputStream fis = new FileInputStream(new File(fileName));
    FileChannel channel = fis.getChannel();
    ByteBuffer bb = ByteBuffer.allocate((int) channel.size());
    channel.read(bb);
    byte[] beforeBytes = bb.array();

    List<String> classNameList = Lists.newArrayList("me.darcy.compress.Bzip2Utils",
        "me.darcy.compress.DeflateUtils", "me.darcy.compress.GzipUtils", "me.darcy.compress.Lz4Utils",
        "me.darcy.compress.LzoUtils", "me.darcy.compress.SnappyUtils");
    for (String className : classNameList) {
      Class<?> aClass = Class.forName(className);
      Method compressMethod = aClass.getMethod("compress", byte[].class);
      Method uncompressMethod = aClass.getMethod("uncompress", byte[].class);

      int times = 200;
      System.out.println(aClass.getName());
      System.out.println("压缩前大小：" + beforeBytes.length + " bytes");
      long startTime1 = System.currentTimeMillis();
      byte[] afterBytes = null;
      for (int i = 0; i < times; i++) {
        afterBytes = (byte[]) compressMethod.invoke(null, beforeBytes);
      }
      long endTime1 = System.currentTimeMillis();
      System.out.println("压缩后大小：" + afterBytes.length + " bytes");
      System.out.println("压缩次数：" + times + "，时间：" + (endTime1 - startTime1)
          + "ms");

      byte[] resultBytes = null;
      long startTime2 = System.currentTimeMillis();
      for (int i = 0; i < times; i++) {
        resultBytes = (byte[]) uncompressMethod.invoke(null, afterBytes);
      }
      System.out.println("解压缩后大小：" + resultBytes.length + " bytes");
      long endTime2 = System.currentTimeMillis();
      System.out.println("解压缩次数：" + times + "，时间：" + (endTime2 - startTime2)
          + "ms");
      System.out.println();
    }
  }

}
