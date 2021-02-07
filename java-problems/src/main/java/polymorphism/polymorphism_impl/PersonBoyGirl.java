package polymorphism.polymorphism_impl;

/**
 * Author by darcy
 * Date on 17-9-5 下午4:31.
 * Description:
 */

class Person {
  public String toString() {
    return "I'm a person.";
  }

  public void eat() {
  }

  public void speak() {
  }

}

class Boy extends Person {
  public String toString() {
    return "I'm a boy";
  }

  public void speak() {
  }

  public void fight() {
  }
}

class Girl extends Person {
  public String toString() {
    return "I'm a girl";
  }

  public void speak() {
  }

  public void sing() {
  }
}

class Party {
  void happyHour() {
    Person girl = new Girl();
    girl.speak();
  }
}

public class PersonBoyGirl {
}

/*
可以看到，Girl 和 Boy 的方法表包含继承自 Object 的方法，继承自直接父类 Person 的方法及各自新定义的方法。
注意方法表条目指向的具体的方法地址，如 Girl 继承自 Object 的方法中，只有 toString() 指向自己的实现
（Girl 的方法代码），其余皆指向 Object 的方法代码；其继承自于 Person 的方法 eat() 和 speak() 分别
指向 Person 的方法实现和本身的实现。

**如果子类改写了父类的方法，那么子类和父类的那些同名的方法共享一个方法表项。**

因此，方法表的偏移量总是固定的。所有继承父类的子类的方法表中，其父类所定义的方法的偏移量也总是一个定值。

Person 或 Object中的任意一个方法，在它们的方法表和其子类 Girl 和 Boy 的方法表中的位置 (index) 是一样的。
这样 JVM 在调用实例方法其实只需要指定调用方法表中的第几个方法即可。

（1）在常量池（这里有个错误，上图为ClassReference常量池而非Party的常量池）中找到方法调用的符号引用 。
（2）查看Person的方法表，得到speak方法在该方法表的偏移量（假设为15），这样就得到该方法的直接引用。
（3）根据this指针得到具体的对象（即 girl 所指向的位于堆中的对象）。
（4）根据对象得到该对象对应的方法表，根据偏移量15查看有无重写（override）该方法，如果重写，
则可以直接调用（Girl的方法表的speak项指向自身的方法而非父类）；如果没有重写，则需要拿到按照
继承关系从下往上的基类（这里是Person类）的方法表，同样按照这个偏移量15查看有无该方法。
*/