package basic;

/**
 * Author by darcy
 * Date on 17-9-9 下午8:57.
 * Description:
 *
 * 父类静态变量, 父类静态代码块, 子类静态变量, 子类静态代码块,
 * 父类非静态变量, 父类非静态代码块, 父类构造函数; 子类静态变量, 子类静态代码块,子类构造函数.
 */

class Father {
  static {
    System.out.println("father static-block");
  }

  Father() {
    System.out.println("father constuctor");
  }

  // 跟构造函数同名.
  public void Father() {
    System.out.println("java.");
  }

  {

    System.out.println("father non-static-block");
  }
}

class Son extends Father {
  static {
    System.out.println("son static-block");
  }

  Son() {
    super();
    // this(); not first statement in block.
    System.out.println("son constuctor");
  }

  {
    System.out.println("son non-static-block");
  }
}

public class StaticNonStaicConstructor {

  public static void main(String[] args) {
    Son son = new Son();
    /*
    father static-block
    son static-block
    father non-static-block
    father constuctor
    son non-static-block
    son constuctor
     */
  }

}
