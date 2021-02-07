package basic;

/**
 * Author by darcy
 * Date on 17-9-9 下午10:24.
 * Description:
 * 1. Thread.slee(), 使得线程暂停一段时间, 从而其他线程可以获取CPU的执行机会.
 *    Object.wait()则是用于线程之间的通信.
 * 2. sleep()不会释放锁, wait()方法则会释放锁.
 * 3. sleep()方法必须捕获异常, wait(), notify(), notifyAll()不需要捕获异常.
 *    sleep过程中,可能被其他线程interrupt()中断, 产生InterruptedException异常.
 *    sleep不会释放锁标志, 所以容易导致死锁问题产生.
 *
 *
 * 1. yield方法只会给更高的优先级或者同级的线程以执行机会
 * 2. sleep方法使得线程进入阻塞状态, yield方法则是使得线程进入就绪状态,
 * 所以yield可能使得线程进入就绪状态后立马又被执行.
 * 3. sleep()方法抛出InterruptedException异常, yield不需要捕获异常.
 * 4. sleep()移植性更好.
 *
 */
public class SleepWait {
}
