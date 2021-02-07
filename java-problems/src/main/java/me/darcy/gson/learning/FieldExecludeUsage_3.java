package me.darcy.gson.learning;

import com.google.common.collect.Lists;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.darcy.utils.PrintUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * https://www.jianshu.com/p/0e40a52c0063
 *
 */
/*
1. 字段过滤Gson中比较常用的技巧，特别是在Android中，在处理业务逻辑时可能需要在设置的POJO中加入一些字段，但显然在序列化的过程中是不需要的，
并且如果序列化还可能带来一个问题就是 循环引用 ，那么在用Gson序列化之前为不防止这样的事件情发生，你不得不作另外的处理。
{
  "id": 1,
  "name": "电脑",
  "children": [
    {
      "id": 100,
      "name": "笔记本"
    },
    {
      "id": 101,
      "name": "台式机"
    }
  ]
}

// 在子分类中保存父分类
public class Category {
    public int id;
    public String name;
    public List<Category> children;
    //因业务需要增加，但并不需要序列化
    public Category parent;
}

问题：在Gson中如何排除符合条件的字段呢?

 */
public class FieldExecludeUsage_3 {

  /**
   * 该注解 @Expose 必须和GsonBuilder配合使用。
   * 使用方法： 简单说来就是需要导出的字段上加上@Expose 注解，不导出的字段不加。注意是不导出的不加。
   * @Expose //
   * @Expose(deserialize = true,serialize = true) //序列化和反序列化都都生效，等价于上一条
   * @Expose(deserialize = true,serialize = false) //反序列化时生效
   * @Expose(deserialize = false,serialize = true) //序列化时生效
   * @Expose(deserialize = false,serialize = false) // 和不写注解一样
   */
  public static void testExpose() {

    Category second = new Category();
    Category first = Category.builder().id(1).name("parent").children(Lists.newArrayList(second)).build();
    second.setId(2);
    second.setName("second");
    second.setParent(first);
    second.setChildren(Lists.newArrayList(Category.builder().id(3).name("id333").build(), Category.builder().id(4).name("id444").build()));

    Gson gson = new GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .setPrettyPrinting()
        .create();
    String secondJsonStr = gson.toJson(second);
    System.out.println(secondJsonStr);
  }


  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SinceUntilSample {
    @Since(4)
    public String since;
    @Until(5)
    public String until;
  }


  /**
   * Gson在对基于版本的字段导出提供了两个注解 @Since 和 @Until,和GsonBuilder.setVersion(Double)配合使用。
   * @Since 和 @Until都接收一个Double值。
   *
   * 使用方法：当前版本(GsonBuilder中设置的版本) 大于等于Since的值时该字段导出，小于Until的值时该该字段导出。
   * //当version <4 时，结果：{"until":"until"}
   * //当version >=4 && version <5时，结果：{"since":"since","until":"until"}
   * //当version >=5 时，结果：{"since":"since"}
   */
  public static void testVersion(double version) {
    SinceUntilSample sinceUntilSample = new SinceUntilSample();
    sinceUntilSample.since = "since";
    sinceUntilSample.until = "until";
    Gson gson = new GsonBuilder().setVersion(version).create();
    System.out.println(gson.toJson(sinceUntilSample));
  }


  static class ModifierSample {
    final String finalField = "final";
    static String staticField = "static";
    public String publicField = "public";
    protected String protectedField = "protected";
    String defaultField = "default";
    private String privateField = "private";
  }

  /**
   * 基于访问修饰符
   * public、static 、final、private、protected
   * GsonBuilder.excludeFieldsWithModifiers构建gson,支持int形的可变参数，值由java.lang.reflect.Modifier提供，
   * 下面的程序排除了privateField 、 finalField 和staticField 三个字段。
   *
   *
   */
  public static void testAccess() {
    ModifierSample modifierSample = new ModifierSample();
    Gson gson = new GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.STATIC, Modifier.PRIVATE)
        .create();
    System.out.println(gson.toJson(modifierSample));
    // 结果：{"publicField":"public","protectedField":"protected","defaultField":"default"}
  }

  /**
   * 基于策略是利用Gson提供的ExclusionStrategy接口，同样需要使用GsonBuilder,相关API 2个，
   * 分别是addSerializationExclusionStrategy 和addDeserializationExclusionStrategy 分别针对序列化和反序化时。这里以序列化为例。
   */
  public static void testStrategy() {
    Gson gson = new GsonBuilder()
        .addSerializationExclusionStrategy(new ExclusionStrategy() {
          @Override
          public boolean shouldSkipField(FieldAttributes f) {
            // 这里作判断，决定要不要排除该字段,return true为排除
            if ("finalField".equals(f.getName())) return true; // 按字段名排除
            Expose expose = f.getAnnotation(Expose.class);
            if (expose != null && expose.deserialize() == false) return true; // 按注解排除
            return false;
          }
          @Override
          public boolean shouldSkipClass(Class<?> clazz) {
            // 直接排除某个类 ，return true 为排除
            return (clazz == int.class || clazz == Integer.class);
          }
        })
        .create();
  }



  /**
   * 2. POJO与JSON的字段映射规则
   *  映射规则那么说的自然是有规律的情况。
   *  GsonBuilder提供了FieldNamingStrategy接口和setFieldNamingPolicy和setFieldNamingStrategy 两个方法。
   *  默认实现
   *
   *  GsonBuilder.setFieldNamingPolicy 方法与Gson提供的另一个枚举类FieldNamingPolicy配合使用，该枚举类提供了5种实现方式分别为：
   *
   *  针对
   *  User user = new User("怪盗kidou", 24);
   *  user.emailAddress = "ikidou@example.com";
   *  例子的输出为:
   *
   * IDENTITY
   * {"emailAddress":"ikidou@example.com"}
   *
   * LOWER_CASE_WITH_DASHES
   * {"email-address":"ikidou@example.com"}
   *
   * LOWER_CASE_WITH_UNDERSCORES
   * {"email_address":"ikidou@example.com"}
   *
   * UPPER_CAMEL_CASE
   * {"EmailAddress":"ikidou@example.com"}
   *
   * UPPER_CAMEL_CASE_WITH_SPACES
   * {"Email Address":"ikidou@example.com"}
   *
   * 自定义实现
   * GsonBuilder.setFieldNamingStrategy 方法需要与Gson提供的FieldNamingStrategy接口配合使用，
   * 用于实现将POJO的字段与JSON的字段相对应。上面的FieldNamingPolicy实际上也实现了FieldNamingStrategy接口，
   * 也就是说FieldNamingPolicy也可以使用setFieldNamingStrategy方法。
   *
   *  @SerializedName注解拥有最高优先级，在加有@SerializedName注解的字段上FieldNamingStrategy不生效！
   *
   */
  public static void testFieldMap() {
    Gson gson = new GsonBuilder()
        .setFieldNamingStrategy(new FieldNamingStrategy() {
          @Override
          public String translateName(Field f) {
            //实现自己的规则
            return null;
          }
        })
        .create();
  }



  public static void main(String[] args) {
    PrintUtils.printlnDash();
    PrintUtils.println("testExpose");
    testExpose();

    PrintUtils.printlnDash("\n");
    PrintUtils.println("testVersion");
    testVersion(3);
    testVersion(4);
    testVersion(5);

    PrintUtils.printlnDash("\n");
    PrintUtils.println("testAccess");
    testAccess();

  }

}
