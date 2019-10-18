package basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectorsToMapMethodErrorCommonSolution {

  public static void main(String[] args) {

    List<Map<String, Object>> list = new ArrayList<>();

    Map<String, Object> map = new HashMap<>();
    map.put("id", "11");
    map.put("orderno", "No.1");
    list.add(map);

    Map<String, Object> map1 = new HashMap<>();
    map1.put("id", "2222");
    map1.put("orderno", "No.2");
    list.add(map1);

    Map<String, Object> map2 = new HashMap<>();
    map2.put("id", "33");
    map2.put("orderno", "No.2");
    list.add(map2);

    Map<String, Object> map3 = new HashMap<>();
    map3.put("id", "444");
    map3.put("orderno", "No.1");
    list.add(map3);

    List<Map<String, Object>> collect = list.stream().filter(distinctByKey(n -> n.get("orderno")))
        .collect(Collectors.toList());

    collect.forEach(System.out::println);
  }

  private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    ConcurrentHashMap<Object, Boolean> map = new ConcurrentHashMap<>();
    return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

}
