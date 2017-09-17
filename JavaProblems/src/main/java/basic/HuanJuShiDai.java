package basic;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author by darcy
 * Date on 17-9-17 下午5:08.
 * Description:
 *
 * 欢聚时代的几个不确定.
 */
public class HuanJuShiDai {

  public static void main(String[] args) {
    /**
     * o 赋值为null, 运行成功.
     * o 不赋值, 编译时错误.
     */
    Object o = null;
    System.out.println("o=" + o);


    /**
     * <>中?,? super Integer, ? extends List都是编译不通过.
     *
     */
    List list = new ArrayList<>();

    /**
     * HTTP协议中共定义了八种方法或者叫“动作”get，post，put， options，head，delete，trace，connect，来表明对Request-URI指定的资源的不同操作方式，具体介绍如下：
     * OPTIONS：返回服务器针对特定资源所支持的HTTP请求方法。也可以利用向Web服务器发送’*’的请求来测试服务器的功能性。
     * HEAD：向服务器索要与GET请求相一致的响应，只不过响应体将不会被返回。这一方法可以在不必传输整个响应内容的情况下，就可以获取包含在响应消息头中的元信息。
     * GET：向特定的资源发出请求。
     * POST：向指定资源提交数据进行处理请求（例如提交表单或者上传文件）。数据被包含在请求体中。POST请求可能会导致新的资源的创建和/或已有资源的修改。
     * PUT：用于向服务器发送请求，如果URI不存在，则要求服务器根据请求创建资源，如果存在，服务器就接受请求内容，并修改URI的原始版本
     * DELETE：请求服务器删除Request-URI所标识的资源。
     * TRACE：回显服务器收到的请求，主要用于测试或诊断。
     * CONNECT：HTTP/1.1协议中预留给能够将连接改为管道方式的代理服务器。
     */

    /**
     * 方法区是JDK1.6引入的.
     */

    // 线程状态
    // https://fangjian0423.github.io/2016/06/04/java-thread-state/

    /**
     * 1. 新建(new)：新创建了一个线程对象。

     2. 可运行(runnable)：线程对象创建后，其他线程(比如main线程）调用了该对象的start()方法。该状态的线程位于可运行线程池中，等待被线程调度选中，获取cpu 的使用权 。

     3. 运行(running)：可运行状态(runnable)的线程获得了cpu 时间片（timeslice） ，执行程序代码。

     4. 阻塞(block)：阻塞状态是指线程因为某种原因放弃了cpu 使用权，也即让出了cpu timeslice，暂时停止运行。直到线程进入可运行(runnable)状态，才有机会再次获得cpu timeslice 转到运行(running)状态。阻塞的情况分三种：

     (一). 等待阻塞：运行(running)的线程执行o.wait()方法，JVM会把该线程放入等待队列(waitting queue)中。

     (二). 同步阻塞：运行(running)的线程在获取对象的同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入锁池(lock pool)中。

     (三). 其他阻塞：运行(running)的线程执行Thread.sleep(long ms)或t.join()方法，或者发出了I/O请求时，JVM会把该线程置为阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入可运行(runnable)状态。

     5. 死亡(dead)：线程run()、main() 方法执行结束，或者因异常退出了run()方法，则该线程结束生命周期。死亡的线程不可再次复生。
     */

    /**
     *
     NEW: 线程创建之后，但是还没有启动(not yet started)。这时候它的状态就是NEW
     RUNNABLE: 正在Java虚拟机下跑任务的线程的状态。在RUNNABLE状态下的线程可能会处于等待状态，
        因为它正在等待一些系统资源的释放，比如IO
     BLOCKED: 阻塞状态，等待锁的释放，比如线程A进入了一个synchronized方法，线程B也想进入这个
        方法，但是这个方法的锁已经被线程A获取了，这个时候线程B就处于BLOCKED状态
     WAITING: 等待状态，处于等待状态的线程是由于执行了3个方法中的任意方法。
        1. Object的wait方法，并且没有使用timeout参数;
        2. Thread的join方法，没有使用timeout参数
        3. LockSupport的park方法。 处于waiting状态的线程会等待另外一个线程处理特殊的行为。
           再举个例子，如果一个线程调用了一个对象的wait方法，那么这个线程就会处于waiting状态
           直到另外一个线程调用这个对象的notify或者notifyAll方法后才会解除这个状态
     TIMED_WAITING: 有等待时间的等待状态，比如调用了以下几个方法中的任意方法，并且指定了等待时间，线程就会处于这个状态。
        1. Thread.sleep方法
        2. Object的wait方法，带有时间
        3. Thread.join方法，带有时间
        4. LockSupport的parkNanos方法，带有时间
        5. LockSupport的parkUntil方法，带有时间
     TERMINATED: 线程中止的状态，这个线程已经完整地执行了它的任务
     */



  }

}
