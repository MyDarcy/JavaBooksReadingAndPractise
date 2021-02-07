package basic;

import java.io.IOException;

/**
 * Author by darcy
 * Date on 17-9-7 下午10:07.
 * Description:
 *
 * Error表示运行期间出现了严重的错误，不可恢复，属于JVM层级的严重错误。
 * Exception表示可恢复的异常,编译器可以捕获, 包括检查异常（Checked Exception）和运行时异常(RuntimeException)
 *
 * CheckedExcption: 发生在编译阶段, 常见的包括SQL/IOException.Java编译器强制程序取捕获此类型的异常，
 * 这种异常的发生可能不会导致程序的出错. 继续处理后可以继续执行.
 *
 * RuntimeException: 编译器并没有强制对其进行捕获并处理.如果不对这种异常进行处理，那么出现这种异常时候，会由JVM进行异常处理。、
 * 包括ClassCast/NullPointer/ArrayStore/ArrayIndexOutOfBound/BufferOverflowException等。
 *
 * 出现运行时异常的时候, 会一直往最上层抛出, 如果没有处理，则抛出到最上层，如果是多线程，则由Thread.run()方法抛出，如果是单线程，
 * 那么main()方法抛出. 如果是线程的话，那么这个线程就退出了，如果是主程序抛出，那么整个程序也退出了。所以如果不对运行时异常进行处理，
 * 后果非常严重，一旦发生，要么线程终止，要么主程序终止.
 *
 */
public class ExceptionTest {

  public void arithmaticTest() throws ArithmeticException {
    System.out.println("Arith.");
  }

  public void ioTest() throws IOException {
    System.out.println("io");
  }

  public static void main(String[] args) {
    ExceptionTest demo = new ExceptionTest();
    demo.arithmaticTest();

    // 编译错误.
    /*demo.ioTest();*/

  }

}
