package basic.leak;

import java.util.HashSet;
import java.util.Set;

/**
 * Author by darcy
 * Date on 17-9-13 下午9:45.
 * Description:
 */
public class Person {

  private String name;
  private String passwd;
  private int age;

  public Person(String name, String passwd, int age) {
    this.name = name;
    this.passwd = passwd;
    this.age = age;
  }

  public Person() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPasswd() {
    return passwd;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public int hashCode() {
    return name.hashCode() + passwd.hashCode() + age;
  }

  public static void main(String[] args) {
    Set<Person> persons = new HashSet<>();
    Person p1 = new Person("AAAA", "aaaa", 25);
    Person p2 = new Person("BBBB", "bbbb", 27);
    Person p3 = new Person("CCCC", "cccc", 30);
    persons.add(p1);
    persons.add(p2);
    persons.add(p3);
    System.out.println("size:" + persons.size()); // 3

    p3.setAge(31);
    persons.remove(p3); // 移除不掉.
    persons.add(p3); // 添加成功.

    System.out.println("size:" + persons.size()); // 4
  }
}
