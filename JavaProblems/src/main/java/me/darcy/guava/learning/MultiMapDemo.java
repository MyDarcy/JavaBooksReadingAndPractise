package me.darcy.guava.learning;

import com.google.common.collect.*;
import me.darcy.utils.PrintUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/**
 * 可以认为MultiMap<Integer, Integer>是Map<Integer, List<Integer>>的简便实现;
 */
public class MultiMapDemo {

  public static void hashMultiMapDemo() {
    Multimap<Integer, Integer> map = HashMultimap.create();
    map.put(1, 2);
    map.put(1, 2);
    map.put(1, 3);
    map.put(1, 4);
    map.put(2, 3);
    map.put(3, 3);
    map.put(4, 3);
    map.put(5, 3);
    System.out.println(map.size()); // 7
    System.out.println(map);
    //判断集合中是否存在key-value为指定值得元素
    System.out.println(map.containsEntry(1, 2));
    System.out.println(map.containsEntry(1, 6));
    //获取key为1的value集合
    Collection<Integer> list = map.get(1);
    System.out.println(list);
    //返回集合中所有key的集合，重复的key将会用key * num的方式来表示
    Multiset<Integer> set = map.keys();
    System.out.println(set);
    System.out.println(set.size()); // 7
    //返回集合中所有不重复的key的集合
    Set<Integer> kset = map.keySet();
    System.out.println(kset);
    /** output
     * {1=[4, 2, 3], 2=[3], 3=[3], 4=[3], 5=[3]}
     * true
     * false
     * [4, 2, 3]
     * [1 x 3, 2, 3, 4, 5]
     * [1, 2, 3, 4, 5]
     */

    // replaceValues方法会替换掉key的value值，并且返回之前对应的值。
    Collection<Integer> coll = map.replaceValues(1, Arrays.asList(1, 7, 8, 9, 10));
    System.out.println(coll);
    System.out.println(map);
    /**
     * [4, 2, 3] // 返回替换之前的值;
     * {1=[8, 1, 9, 10, 7], 2=[3], 3=[3], 4=[3], 5=[3]}
     */
  }

  /**
   * ImmutableMultimap中提供了三个主要的静态方法of、builder、copyof;
   */
  public static void immutableMultiMapDemo() {
    //创建一个静态不可变的Multimap对象
    Multimap<Integer, Integer> map = ImmutableMultimap.of();
    Multimap<Integer, Integer> map1 = ImmutableMultimap.<Integer, Integer>builder().build(); // 类型必须;
    //从另外一个集合中复制数据元素到Multimap对象中
    Multimap<Integer, Integer> map2 = ImmutableMultimap.copyOf(map);
  }

  /**
   * LinkedHashMultimap保存了记录的插入顺序，在使用Iterator循环遍历的时候先得到的肯定是先放入Multimap中的数据。
   */
  public static void linkedHashMultimapDemo() {
    Multimap<Integer, Integer> map = LinkedHashMultimap.create();
    map.putAll(4, Arrays.asList(5, 3, 4, 2, 1, 56));
    map.putAll(3, Arrays.asList(3, 4, 2, 6, 8, 7));
    map.put(1, 2);
    System.out.println(map);
    /**
     * {4=[5, 3, 4, 2, 1, 56], 3=[3, 4, 2, 6, 8, 7], 1=[2]}
     */
  }

  /**
   * TreeMultimap类继承成了Multimap接口，它的所有方法跟HashMultimap一样，
   * 但是有一点不同的是该类实现了SortedSetMultimap接口，该接口会将存入的数据按照自然排序，默认是升序。
   */
  public static void treeMultiMapDemo() {
    Multimap<Integer, Integer> map = TreeMultimap.create();
    map.putAll(4, Arrays.asList(5, 3, 4, 2, 1, 56));
    map.putAll(3, Arrays.asList(3, 4, 2, 6, 8, 7));
    map.put(1, 2);
    System.out.println(map);
    /** output
     * {1=[2], 3=[2, 3, 4, 6, 7, 8], 4=[1, 2, 3, 4, 5, 56]}
     */
  }

  public static void main(String[] args) {
    PrintUtils.println("hashMultiMapDemo");
    hashMultiMapDemo();

    PrintUtils.printlnDash();

    PrintUtils.println("immutableMultiMapDemo");
    immutableMultiMapDemo();

    PrintUtils.printlnDash();

    PrintUtils.println("linkedHashMultimapDemo");
    linkedHashMultimapDemo();

    PrintUtils.printlnDash();
    PrintUtils.println("TreeMultimap");
    treeMultiMapDemo();


  }

}
