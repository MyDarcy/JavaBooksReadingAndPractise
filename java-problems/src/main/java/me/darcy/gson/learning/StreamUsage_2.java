package me.darcy.gson.learning;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.darcy.utils.PrintUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

/**
 *
 * https://www.jianshu.com/p/c88260adaf5e
 *
 * Gson的流式反序列化
 * Gson的流式序列化
 * 使用GsonBuilder导出null值、格式化输出、日期时间及其它小功能
 *
 * Gson的序列化和反序列化（常用）
 * - Gson.toJson(Object);
 * - Gson.fromJson(Reader,Class);
 * - Gson.fromJson(String,Class);
 * - Gson.fromJson(Reader,Type);
 * - Gson.fromJson(String,Type);
 */
public class StreamUsage_2 {


  public static void testJsonReader() {
    try {
      String json = "{\"name\":\"怪盗kidou\",\"age\":\"24\"}";
      User user = new User();
      JsonReader reader = new JsonReader(new StringReader(json)); // JsonReader & StringReader
      reader.beginObject(); // throws IOException
      while (reader.hasNext()) {
        String s = reader.nextName();
        switch (s) {
          case "name":
            user.name = reader.nextString();
            break;
          case "age":
            user.age = reader.nextInt(); //自动转换
            break;
          case "email":
            user.email = reader.nextString();
            break;
        }
      }
      reader.endObject(); // throws IOException
      System.out.println(user.name);  // 怪盗kidou
      System.out.println(user.age);   // 24
      System.out.println(user.email); // ikidou@example.com
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gson的流式反序列化
   * 自动方式
   * PrintStream(System.out) 、StringBuilder、StringBuffer和*Writer都实现了Appendable接口。
   *
   * 除了beginObject、endObject还有beginArray和endArray，两者可以相互嵌套，注意配对即可。
   * beginArray后不可以调用name方法，同样beginObject后在调用value之前必须要调用name方法。
   *
   */
  public static void testToJsonSystomOut() {
    Gson gson = new Gson();
    User user = new User("怪盗kidou",24,"ikidou@example.com");
    gson.toJson(user, System.out); // 写到控制台
    System.out.println();

    // 等价于
    try {
      JsonWriter writer = new JsonWriter(new OutputStreamWriter(System.out));
      writer.beginObject() // throws IOException
          .name("name").value("怪盗kidou")
          .name("age").value(24)
          .name("email").nullValue() //演示null
          .endObject(); // throws IOException
      writer.flush(); // throws IOException
      //{"name":"怪盗kidou","age":24,"email":null}

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 3. 使用GsonBuilder导出null值、格式化输出、日期时间
   * 一般情况下Gson类提供的 API已经能满足大部分的使用场景，但我们需要更多更特殊、更强大的功能时，这时候就引入一个新的类 GsonBuilder。
   * GsonBuilder从名上也能知道是用于构建Gson实例的一个类，要想改变Gson默认的设置必须使用该类配置Gson。
   *
   *
   */
  public static void testGsonBuilders() {
    Gson gson = new Gson();
    User user = new User("怪盗kidou",24);
    System.out.println(gson.toJson(user)); //{"name":"怪盗kidou","age":24}
    System.out.println();

    // null值也会序列化;
    Gson gson2 = new GsonBuilder()
        .serializeNulls()
        .setPrettyPrinting()
        .create();
    User user2 = new User("怪盗kidou", 24);
    System.out.println(gson2.toJson(user2)); //{"name":"怪盗kidou","age":24,"email":null}


    // 格式化输出、日期
    Gson gson3 = new GsonBuilder()
        //序列化null
        .serializeNulls()
        // 设置日期时间格式，另有2个重载方法
        // 在序列化和反序化时均生效
        .setDateFormat("yyyy-MM-dd")
        // 禁此序列化内部类
        .disableInnerClassSerialization()
        //生成不可执行的Json（多了 )]}' 这4个字符）
        .generateNonExecutableJson()
        //禁止转义html标签
        .disableHtmlEscaping()
        //格式化输出
        .setPrettyPrinting()
        .create();
  }

  public static void main(String[] args) {
    PrintUtils.printlnDash();
    PrintUtils.println("testJsonReader");
    testJsonReader();

    PrintUtils.printlnDash();
    PrintUtils.println("testToJsonSystomOut");
    testToJsonSystomOut();

    PrintUtils.printlnDash("\n");
    PrintUtils.println("testGsonBuilders");
    testGsonBuilders();
  }

}
