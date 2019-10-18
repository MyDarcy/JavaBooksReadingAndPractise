package basic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * https://stackoverflow.com/questions/40039649/why-does-collectors-tomap-report-value-instead-of-key-on-duplicate-key-error
 * https://my.oschina.net/u/3829444/blog/2873147
 */
public class CollectorsToMapMethodError {

  @Data
  @AllArgsConstructor
  static class Milk {
    private Integer key;
    private String value;
  }

  public static void main(String[] args) {
    Milk milk0=new Milk(0,"a");
    Milk milk1=new Milk(1,"b");
    Milk milk2=new Milk(0,"c");
    List<Milk> list=new ArrayList<>();
    list.add(milk0);
    list.add(milk1);
    list.add(milk2);
    //去重
    List<Milk> uniqueMilk = list.stream().collect(
        collectingAndThen(
            toCollection(() -> new TreeSet<>(comparing(n->n.getKey()))),ArrayList::new)
    );

    for (Milk m:uniqueMilk) {
      System.out.println(m.toString());
    }

  }
}
