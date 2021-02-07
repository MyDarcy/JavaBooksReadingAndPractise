package basic;

/**
 * Author by darcy
 * Date on 17-9-9 下午8:54.
 * Description:
 */
public class ObjectTest {
  public static void main(String[] args) {
    Object object = new Object() {
      @Override
      public boolean equals(Object obj) {
        return true;
      }
    };
    System.out.println(object.equals("true"));

  }
}
