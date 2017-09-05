package polymorphism.polymorphism_impl;

/**
 * Author by darcy
 * Date on 17-9-5 下午4:40.
 * Description:
 *
 * http://blog.csdn.net/seu_calvin/article/details/52191321
 *
 */
interface IDance{
  void dance();
}

class Person2 {
  public String toString(){
    return "I'm a person.";
  }
  public void eat(){}
  public void speak(){}

}

class Dancer extends Person2 implements IDance {
  public String toString(){
    return "I'm a dancer.";
  }
  public void dance(){}
}

class Snake implements IDance{
  public String toString(){
    return "A snake."; }
  public void dance(){
    //snake dance
  }
}
public class IDancePerson {
}

/*
继承自接口 IDance 的方法 dance()在类 Dancer 和 Snake 的方法表中的位置已经不一样了，
显然我们无法仅根据偏移量来进行方法的调用。

Java 对于接口方法的调用是采用搜索方法表的方式，如，要在Dancer的方法表中找到dance()方法，
必须搜索Dancer的整个方法表。因为每次接口调用都要搜索方法表，所以从效率上来说，接口方法的
调用总是慢于类方法的调用的。
 */