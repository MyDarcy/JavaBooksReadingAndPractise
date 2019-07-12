package me.darcy.guava.learning;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
  //姓名
  private String name;
  //年龄
  private int age;
  //性别
  private String sex;
}
