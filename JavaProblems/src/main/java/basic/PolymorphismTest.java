package basic;

/**
 * Author by darcy
 * Date on 17-9-9 下午9:21.
 * Description:
 *
 * 类的成员方法才有多态的概念,类的成员变量没有多态的概念.
 * 取决与定义变量的类型(引用的类型而不是引用绑定的目标类型.)
 *
 */

class PolyBase {
  public int value = 1;
}

class PolyDerive extends PolyBase {
  public int value = 2;
}

public class PolymorphismTest {

  public static void main(String[] args) {
    PolyBase base = new PolyDerive();
    System.out.println(base.value); // 1.
  }

}
