package me.darcy.gson.learning;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  //省略其它
  public String name;
  public int age;

  // 为POJO字段提供备选属性名
  // 当多种情况同时出时，以最后一个出现的值为准。
  @SerializedName(value = "emailAddress", alternate = {"email", "email_address"})
  public String email;

  public User(String name, int age) {
    this.name = name;
    this.age = age;
  }
}
