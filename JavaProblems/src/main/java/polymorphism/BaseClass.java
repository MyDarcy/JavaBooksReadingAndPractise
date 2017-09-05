package polymorphism;

/**
 * Author by darcy
 * Date on 17-9-5 下午3:14.
 * Description:
 */
public class BaseClass {
  private void priMethod() {
    System.out.println("BaseClass private method");
  }


  void print() {
    System.out.println("print_BaseClass");
  }

  void baseMethod() {
    this.print();
    this.priMethod();
  }
}

