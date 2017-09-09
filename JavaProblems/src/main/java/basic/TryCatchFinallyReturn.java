package basic;

/**
 * Author by darcy
 * Date on 17-9-7 下午9:44.
 * Description:
 *
 * finally中的语句块在return之前执行.
 *
 */
public class TryCatchFinallyReturn {

  /**
   * finnaly处的return会覆盖别处的return.
   * @return
   */
  public int test() {
    try {
      return 1;
    } catch (Exception e) {
      return 2;
    } finally {
      // finally中的return语句.
      System.out.println("finally");
      return 3;
    }

  }

  /**
   * return返回的时候不是直接返回值，而是返回副本.
   * @return
   */
  public int test2() {
    int result = 1;
    try {
      result = 2;
      // 执行return前先把result的值存储在一个指定的位置，然后再去执行finally块中的代码.
      // 此时修改（(finaly中)）result的值（基本类型）将不会影响到程序的返回结果.
      return result;
    } catch (Exception e) {
      return 0;
    } finally {
      // finally中没有return语句.
      // 此时改变基本类型的数据并不影响返回值.
      result = 3;
      System.out.println("finally");
    }
  }

  /**
   * finally中修改引用类型的数据.
   * @return
   */
  public StringBuffer test3() {
    StringBuffer s = new StringBuffer("Hello, ");
    try {
      return s;
    } catch (Exception e) {
      return null;
    } finally {
      s.append("World.");
      System.out.println("finally");

    }
  }

  /**
   * 进入try块之前就抛出异常并不会执行finally.
   * try中强制exit也不会执行finally.
   * @return
   */
  public void test4() {
    int i = 5 / 0;
    try {
      System.out.println("try");
    } catch (Exception e) {
      System.out.println("exception");
    } finally {
      System.out.println("finally");
    }
  }




  public static void main(String[] args) {
    TryCatchFinallyReturn demo = new TryCatchFinallyReturn();
    int result = demo.test();
    System.out.println(result);

    System.out.println("----");

    int result2 = demo.test2();
    System.out.println(result2);

    System.out.println("---");
    StringBuffer stringBuffer = demo.test3();
    System.out.println(stringBuffer);

    System.out.println("-----");
    demo.test4();

    System.out.println("-----");

  }

}
