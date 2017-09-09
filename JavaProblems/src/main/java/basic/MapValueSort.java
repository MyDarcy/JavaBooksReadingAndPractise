package basic;

import java.util.*;

/**
 * Author by darcy
 * Date on 17-9-8 上午10:29.
 * Description:
 */
public class MapValueSort {

  /**
   * TreeMap按照key降序排列.
   */
  public static void test() {
    Map<String, String> map = new TreeMap<String, String>(
        new Comparator<String>() {
          public int compare(String obj1, String obj2) {
            // 降序排序
            return obj2.compareTo(obj1);
          }
        });
    map.put("cab", "ccccc");
    map.put("abc", "aaaaa");
    map.put("aaa", "bbbbb");
    map.put("bbb", "ddddd");
    map.put("b12", "d2ddd");
    map.put("b22", "dd3dd");
    map.put("b24", "d3ddd");

    Set<String> keySet = map.keySet();
    Iterator<String> iter = keySet.iterator();
    while (iter.hasNext()) {
      String key = iter.next();
      System.out.println(key + ":" + map.get(key));
    }
  }

  /**
   * TreeMap按value升序排列.
   */
  public static void test2() {
    Map<String, String> map = new TreeMap<String, String>();
    map.put("cab", "ccccc");
    map.put("abc", "aaaaa");
    map.put("aaa", "bbbbb");
    map.put("bbb", "d1234");
    map.put("b12", "d2341");
    map.put("b22", "d3412");
    map.put("b24", "d4123");

    //这里将map.entrySet()转换成list
    List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
    //然后通过比较器来实现排序
    Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
      //升序排序
      public int compare(Map.Entry<String, String> o1,
                         Map.Entry<String, String> o2) {
        return o1.getValue().compareTo(o2.getValue());
      }

    });

    for(Map.Entry<String,String> mapping:list){
      System.out.println(mapping.getKey()+":"+mapping.getValue());
    }

  }

  /**
   * HashMap按照值升序排列.
   */
  public static void test3() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("cab", "ccccc");
    map.put("abc", "aaaaa");
    map.put("aaa", "bbbbb");
    map.put("bbb", "d1234");
    map.put("b12", "d2341");
    map.put("b22", "d3412");
    map.put("b24", "d4123");

    List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
    Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
      //升序排序
      public int compare(Map.Entry<String, String> o1,
                         Map.Entry<String, String> o2) {
        return o1.getValue().compareTo(o2.getValue());
      }

    });

    for(Map.Entry<String,String> mapping:list){
      System.out.println(mapping.getKey()+":"+mapping.getValue());
    }
  }

  public static void main(String[] args) {
    test();

    System.out.println("----");

    test2();

    System.out.println("---");

    test3();
  }

}
