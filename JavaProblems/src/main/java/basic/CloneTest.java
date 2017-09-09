package basic;

import java.util.Date;

/**
 * Author by darcy
 * Date on 17-9-9 下午9:08.
 * Description:
 *
 * clone:首先检查类中有无非基本类型成员,没有的话,那么直接返回super.clone()方法即可.
 * 如果有的话,那么要保证类中的所有非基本类型成员变量都实现了深复制.
 *
 *
 * 无论clone的继承结构是什么, super.clone()都会直接或者间接的调用Object.clone()方法.
 */

class CloneObject implements Cloneable {
  private Date birth = new Date();

  public Date getBirth() {
    return birth;
  }

  public void setBirth(Date birth) {
    this.birth = birth;
  }

  public void changeDate() {
    birth.setMonth(4);
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    // return super.clone();
    CloneObject obj = null;
    try {
      // 除引用类型成员都clone了。
      obj = (CloneObject) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }

    // 此时clone出的对象和原对象中的引用类型成员指向相同的对象.
    System.out.println("this.getBirth() == obj.birth:  " + (this.getBirth() == obj.birth));

    // 引用类型的birth的深复制.
    // 引用指向新创建的对象.
    obj.birth = (Date) getBirth().clone(); // 成员的clone.

    // 此时clone出的对象和原对象中的引用类型成员指向不同的对象.
    System.out.println("this.getBirth() == obj.birth:  " + (this.getBirth() == obj.birth));
    return obj;
  }
}

public class CloneTest {

  public static void main(String[] args) throws CloneNotSupportedException {
    CloneObject object1 = new CloneObject();
    CloneObject object2 = (CloneObject) object1.clone();
    object2.changeDate();
    System.out.println(object1.getBirth());
    System.out.println(object2.getBirth());
  }
}
