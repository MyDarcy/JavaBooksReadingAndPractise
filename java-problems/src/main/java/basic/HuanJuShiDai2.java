package basic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author by darcy
 * Date on 17-9-17 下午5:32.
 * Description:
 */
interface Counter {
  int add(String string);

  int get(String string);
}

class MyCounter2 implements Counter {

  @Override
  public synchronized int add(String string) {
    return 0;
  }

  @Override
  public synchronized int get(String string) {
    return 0;
  }
}

/**
 *
 */
class MyCounter implements Counter {


  Map<String, AtomicInteger> map = new ConcurrentHashMap<>();
  Object monitor = new Object();
  AtomicInteger ZERO = new AtomicInteger(0);

  @Override
  public int add(String string) {
    /*map.putIfAbsent()
    return map.get(string).get();*/
    synchronized (monitor) {
      if (!map.containsKey(string)) {
        map.put(string, new AtomicInteger(1));
      }
    }
    return map.get(string).incrementAndGet();
  }

  @Override
  public int get(String string) {
    return map.getOrDefault(string, ZERO).get();
  }
}

public class HuanJuShiDai2 {
}
