package me.darcy.gson.learning;


import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
  @Expose
  public int id;
  @Expose public String name;
  @Expose public List<Category> children;
  //不需要序列化,所以不加 @Expose 注解，
  //等价于 @Expose(deserialize = false, serialize = false)
  public Category parent;
}
