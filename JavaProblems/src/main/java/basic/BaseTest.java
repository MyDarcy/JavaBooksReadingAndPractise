package basic;

import java.util.ArrayList;

/**
 * Author by darcy
 * Date on 17-9-9 下午9:50.
 * Description:
 *
 *  1. 保证不可变性: 在传入引用变量或者get()获取引用类型的成员时候, 通过clone()方法来保证类的不可变性.
 *  2. transient和static修饰的变量不能被序列化.
 */
public class BaseTest {

  public static void main(String[] args) {
    System.out.println("new ArrayList<Integer>() instanceof Object");
    System.out.println(new ArrayList<Integer>() instanceof Object);
    System.out.println();

    /**
     * char转为高级类型int, long时候会自动转换为对应的ASCII码.
     * byte, short, char在参与运算时候会自动转型为int类型，　使用 += 时候就不会发送类型转换.
     */
    char c = 'c';
    c += 1;
    System.out.println(c);

    /*short iShort = 1;
    iShort = iShort + 1; // 不cast会报编译时错误.
    System.out.println();*/

    /**
     * 这两种种写法竟然可以.
     */
    long []array[] = new long[1][3];
    long [][]array2 = new long[2][3];
    long array3[][] = new long[2][3];
    long[][] array4 = new long[1][2];
    System.out.println(array);
    System.out.println(array2);
    System.out.println(array3);
    System.out.println(array4);
  }
}
