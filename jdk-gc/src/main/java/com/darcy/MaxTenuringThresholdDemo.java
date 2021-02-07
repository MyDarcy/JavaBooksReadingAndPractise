package com.darcy;

/**
 * author:hezhiqiang05
 * date time:2020-07-10 19:26
 * description:
 * https://www.jianshu.com/p/2e69aa552b01
 *
 * 参数配置:
 * -Xmx200M -Xmn50m -XX:TargetSurvivorRatio=60 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:MaxTenuringThreshold=3 -XX:+PrintTenuringDistribution
 *
 * 用法: -XX:MaxTenuringThreshold=3
 * 该参数主要是控制新生代需要经历多少次GC晋升到老年代中的最大阈值。在JVM中用4个bit存储（放在对象头中），所以其最大值是15。
 * 但并非意味着，对象必须要经历15次YGC才会晋升到老年代中。例如，当survivor区空间不够时，便会提前进入到老年代中，但这个次数一定不大于设置的最大阈值。
 *
 * 那么JVM到底是如何来计算S区对象晋升到Old区的呢？
 * 首先介绍另一个重要的JVM参数：
 * -XX:TargetSurvivorRatio：期望Survivor区对象存活大小(Desired survivor size)的参数。默认值为50，即50%。
 * 当一个S区中所有的age对象的大小如果大于等于Desired survivor size，则重新计算threshold，以age和MaxTenuringThreshold两者的最小值为准。
 *
 * 链接：https://www.jianshu.com/p/2e69aa552b01
 *
 * -verbose:gc -XX:MaxTenuringThreshold=3 -XX:PrintGCDetails -XX:+PrintCommandLineFlags -XX:+PrintTenuringDistribution
 *
 * 1M = 1024 * 1024 = 1048576
 * 3145728 = 3M
 */
//-Xmx200M -Xmn50m -XX:TargetSurvivorRatio=60 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:MaxTenuringThreshold=3
//最小堆为50M，默认SurvivorRatio为8，那么可以知道Eden区为40M，S0和S1为5M
public class MaxTenuringThresholdDemo {

  public static final int ONE_MEGA_BYTE = 1 * 1024 * 1024;

  public static void main(String[] args) throws InterruptedException {

    // main方法作为主线程，变量不会被回收
    byte[] byte1 = new byte[ONE_MEGA_BYTE];
    byte[] byte2 = new byte[ONE_MEGA_BYTE];


    printInfo(1);
    YGC(40);
    Thread.sleep(3000);

    printInfo(2);
    YGC(40);
    Thread.sleep(3000);

    printInfo(3);
    YGC(40);
    Thread.sleep(3000);

    printInfo(4);
    // 这次再ygc时, 由于byte1和byte2的年龄经过3次ygc后已经达到3(-XX:MaxTenuringThreshold=3), 所以会晋升到old
    YGC(40);
    // ygc后, s0(from)/s1(to)的空间为0
    Thread.sleep(3000);


    // 达到TargetSurvivorRatio这个比例指定的值,即 5M(S区)*60%(TargetSurvivorRatio)=3M(Desired survivor size)
    byte[] byte4 = new byte[ONE_MEGA_BYTE];
    byte[] byte5 = new byte[ONE_MEGA_BYTE];
    byte[] byte6 = new byte[ONE_MEGA_BYTE];

    printInfo(5);
    // 这次ygc时, 由于s区已经占用达到了60%(-XX:TargetSurvivorRatio=60),
    // 所以会重新计算对象晋升的min(age, MaxTenuringThreshold) = 1
    YGC(40);
    Thread.sleep(3000);

    // 由于前一次ygc时算出age=1, 所以这一次再ygc时, byte4, byte5, byte6就要晋升到Old,
    // 而不需要等MaxTenuringThreshold这么多次, 此次ygc后, s0(from)/s1(to)的空间再次为0, 对象全部晋升到old
    YGC(40);
    Thread.sleep(3000);

    System.out.println("GC end!");
  }

  //塞满Eden区，局部变量会被回收,作为触发GC的小工具
  private static void YGC(int edenSize){
    for (int i = 0 ; i < edenSize ; i ++) {
      byte[] byte1m = new byte[1 * 1024 * 1024];
    }
  }

  public static final void printInfo(int i) {
    System.out.println("\nop:" + i);
  }

}
