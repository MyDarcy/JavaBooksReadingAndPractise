package LearnJDK;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Author by darcy
 * Date on 17-9-15 上午11:08.
 * Description:
 */
public class HashMapTest {

  /**
   * The maximum capacity, used if a higher value is implicitly specified
   * by either of the constructors with arguments.
   * MUST be a power of two <= 1<<30.
   */
  static final int MAXIMUM_CAPACITY = 1 << 30;

  static final int tableSizeFor(int cap) {
    int n = cap - 1;
    // >>> : 无符号右移，忽略符号位，空位都以0补齐
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
  }


  public static void main(String[] args) {
    Random random = new Random(31);
    for (int i = 0; i < 30; i++) {
      int oldCap = random.nextInt(10000);
      int cap = tableSizeFor(oldCap);
      System.out.println(oldCap + "->" + cap);
    }


    // 固定大小的LRU.重写removeEldestEntry即可.
    int cacheSize = 3;
    LinkedHashMap<String, Integer> lru = new LinkedHashMap<String, Integer>(
        16, 0.75f, true) {
      @Override
      protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > cacheSize;
      }
    };
    lru.put("Java", 1995);
    lru.put("Python", 1989);
    lru.put("Go", 2009);
    lru.put("C++", 1989);

    System.out.println(lru);

    lru.get("Go");
    lru.get("Python");
    System.out.println(lru);

  }

}
