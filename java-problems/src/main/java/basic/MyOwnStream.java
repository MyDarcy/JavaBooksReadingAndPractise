package basic;

import java.io.*;

/**
 * Author by darcy
 * Date on 17-9-8 上午9:28.
 * Description:
 *
 * 大写-> 小写;
 * 小写-> 大写
 * 其他不变.
 *
 * 字符流在输入输出的时候会用到缓存.
 *
 * IO流的装饰器模式,
 * ByteArrayInputStream, FileInputStream, PipedInputSteam是基本的流处理类型;
 * BufferedInputStrea则对基本的流类型进行包装, 运行时添加更复杂的行为.
 *
 */
public class MyOwnStream extends FilterInputStream {
  /**
   * Creates a <code>FilterInputStream</code>
   * by assigning the  argument <code>in</code>
   * to the field <code>this.in</code> so as
   * to remember it for later use.
   *
   * @param in the underlying input stream, or <code>null</code> if
   *           this instance is to be created without an underlying stream.
   */
  protected MyOwnStream(InputStream in) {
    super(in);
  }

  @Override
  public int read() throws IOException {
    int c = 0;
    if ((c = super.read()) != -1) {
      if (Character.isLowerCase((char) c)) {
        return Character.toUpperCase((char) c);
      } else if (Character.isUpperCase((char) c)) {
        return Character.toLowerCase((char) c);
      } else {
        return c;
      }
    } else {
      return -1;
    }
  }


  public static void main(String[] args) {
    int c = 0;
    try {
      InputStream input = new MyOwnStream(new BufferedInputStream(/*new StringBufferInputStream("aaaBBBcccDDD123")*/ new FileInputStream("")));
      while ((c = input.read()) != -1) {
        System.out.print((char) c);
      }

      input.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
