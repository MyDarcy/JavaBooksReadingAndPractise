package advance;

import java.util.HashMap;
import java.util.Map;

/**
 * Author by darcy
 * Date on 17-9-20 下午1:49.
 * Description:
 *
 * http://blog.csdn.net/f2006116/article/details/51901811
 *
 * 题目
 操作系统中的虚拟内存管理系统可采用先进先出算法的方式缓存。

 当请求的内存页不在缓存中。且缓存已满时，应从缓存中删除保存时间最长的页面，
 从而为请求页面腾出空间，如果缓存未满，可直接将请求页面添加到缓存中，
 给定的页面最多只应在缓存中出现一次。

 构造FIFO类的构造方法为countCacheMiss。
 该方法输入包括一个整数max_cache_size,和一个页面请求数组page_requests，
 要求方法返回缓存未命中数的总数。

 思考
 可以为每个页面设置一个变量来记录在缓存中的时长，题目又要求页面不重复，
 考虑用HashMap的键值对处理，key表示缓存中页面编号，value表示时长。
 对于每一个页面请求，先判断是不是存在于缓存，
 若存在是则将缓存内所有页面时长+1；
 否则，
 若缓存<max_cache_size时添加当前请求页面至缓存，
 若达到max_cache_size则从缓存中去掉时长最大的那个，再添加当前请求页面至缓存。
 */

/**
 * 这种实现可以,但是都需要更新对象的生命周期.
 */
public class FIFOCache {

  public static int FIFO(int maxize, int[] page_req) {
    int count = 0;
    HashMap<Integer, Integer> pages = new HashMap<Integer, Integer>();
    for (int j = 0; j < page_req.length; j++) {

      // 包含页面.
      if (pages.containsKey(Integer.valueOf(page_req[j]))) {
        System.out.println(Integer.valueOf(page_req[j]) + "命中缓存!");
        // 若pages列表中有当前请求页面，就把列表时间加1；
        for (Map.Entry<Integer, Integer> entry : pages.entrySet()) {
          Integer value = entry.getValue();
          entry.setValue(Integer.valueOf(value.intValue() + 1));
          System.out.print(entry.getKey() + " 当前的保存时间：" + entry.getValue() + "\t");
        }
        System.out.println();
        continue;

        // 不包含页面.
      } else {
        System.out.println(Integer.valueOf(page_req[j]) + "未命中缓存!");
        // 如果缓存满了，则去除缓存中存在最久的页面
        if (pages.size() == maxize) {
          int max = 0;
          int key = 0;
          for (Map.Entry<Integer, Integer> entry1 : pages.entrySet()) {
            // 求出缓存中存在最久的页面
            if (entry1.getValue().intValue() > max) {
              max = entry1.getValue().intValue();
              key = entry1.getKey().intValue();
            }
          }
          // 移除页面
          pages.remove(key);
          System.out.println(key + "被移除！");
        }
        // 就把当前缓存列表中页面的时间加1；
        for (Map.Entry<Integer, Integer> entry : pages.entrySet()) {
          Integer value = entry.getValue();
          entry.setValue(Integer.valueOf(value.intValue() + 1));
        }
        // 将新的请求页面加入缓存列表中并将其请求次数设置为0
        System.out.println(Integer.valueOf(page_req[j]) + "存入缓存!");
        pages.put(Integer.valueOf(page_req[j]), Integer.valueOf(0));
        // 统计缺页次数
        count++;
        System.out.println("当前未命中次数为：" + count);
      }
      // // 输出当前的缓存信息
      System.out.println("当前的缓存信息为：");
      for (Map.Entry<Integer, Integer> entry : pages.entrySet()) {
        Integer value = entry.getValue();
        Integer key = entry.getKey();
        System.out.print(key + " 在缓存中，保存时间：" + value + "\t");
      }
      System.out.println();
    }
    return count;
  }

  public static void main(String[] args) {
    // test 1
    int maxsize = 2;
    int[] page_reg1 = { 1, 2, 1, 3, 1, 2 };
    int count = FIFO(maxsize, page_reg1);
    System.out.println("未命中总数为：" + count);
    System.out.println("——————————————————————————————————");
    // test 2
    maxsize = 3;
    int[] page_reg2 = { 7, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0 };
    count = FIFO(maxsize, page_reg2);
    System.out.println("未命中总数为：" + count);
    System.out.println("——————————————————————————————————");
    // test 3
    maxsize = 2;
    int[] page_reg3 = { 2, 3, 1, 3, 2, 1, 4, 3, 2 };
    count = FIFO(maxsize, page_reg3);
    System.out.println("未命中总数为：" + count);
    System.out.println("——————————————————————————————————");
  }

}

