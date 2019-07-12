package me.darcy.gson.learning;


import com.google.common.collect.Lists;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.darcy.utils.PrintUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.jianshu.com/p/3108f1e44155
 *
 * https://www.jianshu.com/p/d62c2be60617 泛型
 *
 * 本次文章的主要内容：
 *
 * TypeAdapter
 * JsonSerializer与JsonDeserializer
 * TypeAdapterFactory
 * @JsonAdapter注解
 * TypeAdapter与 JsonSerializer、JsonDeserializer对比
 * TypeAdapter实例
 * 结语
 */
public class GsonAdvancedUsage_4 {

  /**
   * 当我们为User.class 注册了 TypeAdapter之后，只要是操作User.class 那些之前介绍的@SerializedName 、FieldNamingStrategy、Since、Until、Expos通通都黯然失色，
   * 失去了效果，只会调用我们实现的UserTypeAdapter.write(JsonWriter, User) 方法，我想怎么写就怎么写。
   *
   */
  public static void testTypeAdapter() {
    User user = new User("怪盗kidou", 24);
    user.email = "ikidou@example.com";
    Gson gson = new GsonBuilder()
        //为User注册TypeAdapter
        .registerTypeAdapter(User.class, new UserTypeAdapter())
        .create();
    System.out.println(gson.toJson(user));

    // 注册一个TypeAdapter 把 序列化和反序列化的过程接管;
    // 这里只integer类型的序列化和反序列化，反序列化失败时提供默认值；
    gson = new GsonBuilder()
        .registerTypeAdapter(Integer.class, new TypeAdapter<Integer>() {
          @Override
          public void write(JsonWriter out, Integer value) throws IOException {
            out.value(String.valueOf(value));
          }
          @Override
          public Integer read(JsonReader in) throws IOException {
            try {
              return Integer.parseInt(in.nextString());
            } catch (NumberFormatException e) {
              return -1;
            }
          }
        })
        .create();
    System.out.println(gson.toJson(100)); // 结果："100"
    System.out.println(gson.fromJson("\"\"",Integer.class)); // 结果：-1
  }

  /**
   * JsonSerializer 和JsonDeserializer 不用像TypeAdapter一样，必须要实现序列化和反序列化的过程，你可以据需要选择，如只接管序列化的过程就用 JsonSerializer
   *
   * registerTypeAdapter必须使用包装类型，所以int.class,long.class,float.class和double.class是行不通的。
   * 同时不能使用父类来替上面的子类型，这也是为什么要分别注册而不直接使用Number.class的原因。
   *
   * 上面特别说明了registerTypeAdapter不行，那就是有其它方法可行咯?当然！换成**registerTypeHierarchyAdapter **就可以使用Number.class而不用一个一个的当独注册啦！
   *
   * 如果一个被序列化的对象本身就带有泛型，且注册了相应的TypeAdapter，那么必须调用Gson.toJson(Object,Type)，明确告诉Gson对象的类型。
   *
   */
  public static void testJsonSerializer() {
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {
          @Override
          public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
              return json.getAsInt();
            } catch (NumberFormatException e) {
              return -1;
            }
          }
        })
        .create();
    System.out.println(gson.toJson(100)); //结果：100
    System.out.println(gson.fromJson("\"\"", Integer.class)); //结果-1

    // 所有数字都徐磊好为字符串
    JsonSerializer<Number> numberJsonSerializer = new JsonSerializer<Number>() {
      @Override
      public JsonElement serialize(Number src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(String.valueOf(src));
      }
    };
    // registerTypeAdapter必须使用包装类型，所以int.class,long.class,float.class和double.class是行不通的。
    // 同时不能使用父类来替上面的子类型，这也是为什么要分别注册而不直接使用Number.class的原因。
    gson = new GsonBuilder()
        .registerTypeAdapter(Integer.class, numberJsonSerializer)
        .registerTypeAdapter(Long.class, numberJsonSerializer)
        .registerTypeAdapter(Float.class, numberJsonSerializer)
        .registerTypeAdapter(Double.class, numberJsonSerializer)
        .create();
    System.out.println(gson.toJson(100.0f));//结果："100.0"

    // 如果一个被序列化的对象本身就带有泛型，且注册了相应的TypeAdapter，那么必须调用Gson.toJson(Object,Type)，明确告诉Gson对象的类型。
    Type type = new TypeToken<List<User>>() {}.getType();
    TypeAdapter typeAdapter = new TypeAdapter<List<User>>() {
      @Override
      public void write(JsonWriter jsonWriter, List<User> users) throws IOException {
        jsonWriter.beginArray();
        for (User user : users) {
          jsonWriter.beginObject();
          jsonWriter.name("name").value(user.name);
          jsonWriter.name("age").value(user.age);
          jsonWriter.name("email").value(user.email);
          jsonWriter.endObject();
        }
        jsonWriter.endArray();
      }

      @Override
      public List<User> read(JsonReader jsonReader) throws IOException {
//        String name = null;
//        int age = -1;
//        String email = null;

        List<User> users = Lists.newArrayList();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
          // remain to be impl;
          // https://futurestud.io/tutorials/gson-mapping-of-arrays-and-lists-of-objects
          // https://github.com/google/gson/issues/1274
          switch (jsonReader.peek()) {
            case BEGIN_OBJECT:
              jsonReader.beginObject();
              User user = new User();
              while (jsonReader.hasNext()) {
                switch (jsonReader.nextName()) {
                  case "name":
                    user.name = jsonReader.nextString();
                    break;
                  case "age":
                    user.age = jsonReader.nextInt();
                    break;
                  case "email":
                  case "email_address":
                  case "emailAddress":
                    user.email = jsonReader.nextString();
                    break;
                }
              }
              jsonReader.endObject();
//              User user1 = new User(name, age, email);
              users.add(user);
              continue;
          }
        }
        return users;
      }
    };
    gson = new GsonBuilder()
        .registerTypeAdapter(type, typeAdapter)
        .create();
    List<User> list = new ArrayList<>();
    list.add(new User("a",11));
    list.add(new User("b",22));
    //注意，多了个type参数
    String result = gson.toJson(list, type);
    System.out.println(result);
//    List<User> resultList = gson.fromJson("[{\"name\":\"a\",\"age\":11},{\"name\":\"b\",\"age\":22}]", type);
//    System.out.println(resultList);
  }

  public static void main(String[] args) {
    PrintUtils.printlnDash();
    PrintUtils.println("testTypeAdapter");
    testTypeAdapter();

    PrintUtils.printlnDash();
    PrintUtils.println("testJsonSerializer");
    testJsonSerializer();


  }

}
