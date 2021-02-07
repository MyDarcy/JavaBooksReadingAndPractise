package me.darcy.jdk8;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
class KeyValue {
  private String key;
  private String value;
}

public class EmptyListToMap {

  public static void main(String[] args) {
    Map<String, KeyValue> map = Maps.newHashMap();
    Map<String, String> result = map.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toMap(item -> item.getKey(), item -> item.getValue()));
    System.out.println(result != null);
    System.out.println(result.size());

    String keyMappingConf = "[]";
    List<KeyValue> keyValueList = JSON.parseArray(keyMappingConf, KeyValue.class);
    System.out.println(keyValueList);
    System.out.println(keyValueList.size());
  }

}
