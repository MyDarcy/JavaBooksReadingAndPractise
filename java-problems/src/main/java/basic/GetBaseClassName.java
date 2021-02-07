package basic;

import java.util.ArrayList;

/**
 * Author by darcy
 * Date on 17-9-9 下午9:33.
 * Description:
 *
 * getClass在Object方法中定义为final和native的, 子类不能覆盖该方法。
 * 因此this.getClass()和super.getClass()最终调用的都是Object.getClass方法,
 * 而Object中的getClass()方法的解释是:返回此Object的运行时类.
 *
 * 所以要在子类中获取父类的名字可以使用反射的机制: getClass().getSuperClass().getName()
 *
 */


 class A {

}

 class Test extends A {
  public void test() {
    System.out.println(super.getClass().getName());
  }

  public void test2() {
    System.out.println(getClass().getSuperclass().getName());
  }
}

public class GetBaseClassName {



  public static void main(String[] args) {
    new Test().test();

    System.out.println();

    new Test().test2();


  }

}
