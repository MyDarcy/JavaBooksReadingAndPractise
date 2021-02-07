package me.darcy.gson.learning;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.darcy.utils.PrintUtils;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * https://www.jianshu.com/p/e740196225a4
 */
public class BasicUsage {

  /**
   * 1. Gson的基本用法
   */
  public static void testBasicType() {
    Gson gson = new Gson();
    int i = gson.fromJson("100", int.class);              //100
    double d = gson.fromJson("\"99.99\"", double.class);  //99.99
    boolean b = gson.fromJson("true", boolean.class);     // true
    String str = gson.fromJson("String", String.class);   // String
    System.out.println(i + "\t" + d + "\t" + b + "\t" + str);
  }

  public static void testPojo() {
    Gson gson = new Gson();
    User user = new User("怪盗kidou", 24);
    String jsonObjectStr = gson.toJson(user); // {"name":"怪盗kidou","age":24}
    System.out.println(jsonObjectStr);
    String originStr = "{\"name\":\"怪盗kidou\",\"age\":24}";
    User userResult = gson.fromJson(originStr, User.class);
    System.out.println(userResult);
  }

  /**
   * 2. 属性重命名 @SerializedName 注解的使用
   * 期望的json格式 {"name":"怪盗kidou","age":24,"emailAddress":"ikidou@example.com"}
   * 实际的json格式 {"name":"怪盗kidou","age":24,"email_address":"ikidou@example.com"}
   *
   * 那么需要在实例中注解:
   * @SerializedName("email_address")
   * public String emailAddress;
   *
   * 但是实际的json可以如下：
   * {"name":"怪盗kidou","age":24,"emailAddress":"ikidou@example.com"}
   * {"name":"怪盗kidou","age":24,"email_address":"ikidou@example.com"}
   * {"name":"怪盗kidou","age":24,"email":"ikidou@example.com"}
   */
  public static void testAnnotation() {
    Gson gson = new Gson();
    String[] array = new String[]{"{\"name\":\"怪盗kidou\",\"age\":24,\"emailAddress\":\"ikidou@example.com\"}",
        "{\"name\":\"怪盗kidou\",\"age\":25,\"email_address\":\"ikidou@example.com\"}",
        "{\"name\":\"怪盗kidou\",\"age\":26,\"email\":\"ikidou@example.com\"}"};
    for (String jsonStr : array) {
      User user = gson.fromJson(jsonStr, User.class);
      System.out.println(user);
    }
  }

  /**
   * 3、Gson中使用泛型
   * 对于List将上面的代码中的 String[].class 直接改为 List<String>.class 是行不通的。
   * 对于Java来说List<String> 和List<User> 这俩个的字节码文件只一个那就是List.class，这是Java泛型使用时要注意的问题 泛型擦除。
   *
   * 为了解决的上面的问题，Gson为我们提供了TypeToken来实现对泛型的支持，所以当我们希望使用将以上的数据解析为List<String>时需要这样写。
   *
   * TypeToken的构造方法是protected修饰的,所以上面才会写成new TypeToken<List<String>>() {}.getType() // 匿名类实现;
   * 而不是  new TypeToken<List<String>>().getType()
   */

  public static void testArray() {
    Gson gson = new Gson();
    String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
    String[] strings = gson.fromJson(jsonArray, String[].class);
    List<String> lists = gson.fromJson(jsonArray, new TypeToken<List<String>>(){}.getType());
    System.out.println(Arrays.toString(strings));
    System.out.println(lists);
  }

  /**
   * user和Lit<user>的字符串是不同的;
   */
  public static void testGeneric() {
    Gson gson = new Gson();
    String json = "{\"code\":0,\"data\":{\"age\":26,\"emailAddress\":\"123@gmail.com\",\"name\":\"darcy\"},\"message\":\"success\"}";
    //不再重复定义Result类
    Type userType = new TypeToken<Result<User>>(){}.getType();
    Result<User> userResult = gson.fromJson(json, userType);
    User user = userResult.data;

    json = "{\"code\":0,\"data\":[{\"age\":26,\"emailAddress\":\"123@gmail.com\",\"name\":\"darcy\"}],\"message\":\"success\"}";
    Type userListType = new TypeToken<Result<List<User>>>(){}.getType();
    Result<List<User>> userListResult = gson.fromJson(json, userListType);
    List<User> users = userListResult.data;

    System.out.println(userResult);
    System.out.println(userListResult);
  }



  public static void main(String[] args) {
    PrintUtils.printlnDash();
    PrintUtils.println("testBasicType");
    testBasicType();

    PrintUtils.printlnDash();
    PrintUtils.println("testPojo");
    testPojo();

    PrintUtils.printlnDash();
    PrintUtils.println("testAnnotation");
    testAnnotation();

    PrintUtils.printlnDash();
    PrintUtils.println("testArray");
    testArray();

    PrintUtils.printlnDash(120);
    PrintUtils.println("testGeneric");
    testGeneric();

//    User user = new User("darcy", 26, "123@gmail.com");
//    Result<User> userResult = new Result<User>(0, "success", user);
//    System.out.println(JSON.toJSONString(userResult));

  }

}
