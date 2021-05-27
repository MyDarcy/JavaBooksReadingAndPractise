package com.darcy.chapter6;

import java.util.LinkedList;

/**
 * 第一次使用jdk11无法编译的问题
 * https://stackoverflow.com/questions/49398894/unable-to-compile-simple-java-10-java-11-project-with-maven/51586202#51586202
 *
 * mvn --version | grep -i java
 * 重新配置jdk11路径;
 */

/**
 java -XX:+UnlockExperimentalVMOptions -XX:+UseZGC -Xms256m -Xmx1g￼Xlog:gc＊=trace:file=simple_quickoom.log:time,tid,tags:filecount=3,filesize=200m￼ QuickOOM
 java -XX:+UnlockExperimentalVMOptions -XX:+UseZGC -Xms256m -Xmx1g￼-Xlog:gc*=info:file=simple_quickoom.log:time:filecount=10,filesize=200M
 -XX:+UnlockExperimentalVMOptions -XX:+UseZGC -Xms256m -Xmx1g￼-Xlog:gc＊:file=simple_quickoom.log:time,tid,tags:filecount=3,filesize=200m
 Xlog:gc＊=trace:file=detail_slowoom.log:time,tid,tags


 persona:
 -XX:+UnlockExperimentalVMOptions -XX:+UseZGC -XX:ZAllocationSpikeTolerance=4 -XX:ParallelGCThreads=5 -XX:ConcGCThreads=1 -XX:ZCollectionInterval=20 -XX:MaxGCPauseMillis=20 -XX:-UseBiasedLocking -XX:+HeapDumpOnOutOfMemoryError -verbose:gc
 -Xms12g -Xmx12g
 -Xlog:gc*=info:file=$LOG_PATH/$MODULE.gc.log:time:filecount=10,filesize=30M

 -XX:ZStatisticsForceTrace=true
 -XX:+UnlockDiagnosticVMOptions

 */
public class QuickOOM {
  private static final LinkedList<String> strings = new LinkedList<>();
  private static final LinkedList<int[]> intArrayList = new LinkedList<>();
  private static int[] mediumObjBuffer;
  private static int[] mediumObjBufferInArray;
  private static int mediumObjSize = 1024 * 512; // 2MB
  private static int mediumObjSizeInArray = 1024 * 256; // 1MB
  private static int[] largeObjBuffer;
  private static int largeObjSize = 1024 * 1024 * 2; // 8MB

  public static void main(String[] args) throws Exception {
    int iteration = 0;

    while (true) {
      //small object, strong root
      for (int i = 0; i < 1000; i++) {
        for (int j = 0; j < 100; j++) {
          strings.add(new String("String " + i * 1000 + j));
        }
      }
      //medium object, grabage
      for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 5; j++) {
          mediumObjBuffer = new int[mediumObjSize];
          mediumObjBufferInArray = new int[mediumObjSizeInArray];
          intArrayList.add(mediumObjBufferInArray);
        }
      }

      //large object, strong root
      for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {
          largeObjBuffer = new int[largeObjSize];
        }
        System.gc();
        Thread.sleep(100);
      }
    }
  }
}
