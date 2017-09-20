package advance;

import java.io.BufferedInputStream;
import java.util.*;

/**
 * Author by darcy
 * Date on 17-9-20 下午1:51.
 * Description:
 * <p>
 * http://blog.csdn.net/fengsigaoju/article/details/53389799
 */
public class PageCache {
  private int cacheSize;//内储页框
  private int pageNumbers;//访问次数
  private int pageMissingTimes;//没能直接找到的次数,(pageMissingTimes/pageNumbers)为缺页率
  private List<Integer> pages = null;//访问地址走向
  private Map<Integer, Integer> cache = null;

  public PageCache() {
    pageMissingTimes = 0;
    cache = new HashMap<Integer, Integer>();//存储每一个内储页框所存的内容
    Scanner input = new Scanner(new BufferedInputStream(System.in));
    System.out.println("请输入用户访问页地址走向");
    pages = new ArrayList<Integer>();
    String s = input.nextLine();
    String[] s1 = s.split(" ");

    pageNumbers = s1.length;
    for (int i = 0; i < pageNumbers; i++)
      pages.add(Integer.valueOf(s1[i]));
    System.out.println("请输入缓存大小");

    // 缓存的大小.
    cacheSize = input.nextInt();
    menu();
    switch (input.nextInt()) {
      case 1:
        OPT();
        break;
      case 2:
        FIFO();
        break;
      case 3:
        LRU();
        break;
    }
    input.close();
  }

  public void menu() {
    System.out.println("**** OPT 请按1 **********");
    System.out.println("**** FIFO 请按2 *******");
    System.out.println("**** LRU 请按3 ***");
  }

  /**
   * 最佳置换算法，其所选择的被淘汰的页面将是以后永不使用的，
   * 或是在最长（未来）时间内不再被访问的页面。采用最佳置换算
   * 法通常可保证最低的缺页率。
   *
   * http://www.jianshu.com/p/544ee20e307c
   */
  public void OPT() {//最佳置换算法
    int j;
    for (int i = 0; i < pageNumbers; i++) {
      int k = pages.get(i);//待处理元素
      //System.out.print(cache);
      if (!cache.containsValue(k)) {
        pageMissingTimes++;//不能直接找到次数加1
        if (cache.size() < cacheSize) {//如果没有装满
          int temp = cache.size();
          cache.put(temp, k);
        } else {//如果装满了
          int index = 0;//把哪个位置的淘汰出去
          int min = 0;//初始最长长度

          // 两次访问同一个元素相距最远的.
          for (int t = 0; t < cacheSize; t++) {
            // 注意是i以后的元素.
            for (j = i + 1; j < pageNumbers; j++) {//看后面哪一个出现的最晚
              if (pages.get(j) == cache.get(t)) {//第一次找到
                if (j - i > min) {
                  index = t;//更新值
                  min = j - i;
                }
                break;
              }
            }
            if (j == pageNumbers) {//如果到最后
              index = t;
              min = j - i;
            }
          }
          cache.remove(index);
          cache.put(index, k);//修改表内元素
        }
      }
    }
    System.out.println("误码率为:" + pageMissingTimes * 1.0 / pageNumbers);
  }

  /**
   * 个人感觉这种实现有问题.
   * 可以用一个全局变量表示页面调度的次数,这个代表页面的生命周期.
   */
  public void FIFO() {//先进先出置换算法
    Queue<Integer> q = new LinkedList<Integer>();
    for (int i = 0; i < pageNumbers; i++) {
      int k = pages.get(i);//待处理元素

      // 在里面不做任何处理.
      if (!cache.containsValue(k)) {
        pageMissingTimes++;//不能直接找到次数加1
        if (cache.size() < cacheSize) {//如果没有装满
          // index也可以看作是对象的生命周期.
          int index = cache.size();
          cache.put(index, k);
          q.offer(index);
        } else {
          int temp = q.poll();//排除的元素位置
          cache.remove(temp);
          cache.put(temp, k);
          q.offer(temp);
        }
      }
    }
    System.out.println("误码率为:" + pageMissingTimes * 1.0 / pageNumbers);
  }

  public void LRU() {//最近最远未使用置换算法
    List<Integer> linkedlist = new LinkedList<Integer>();
    int start = 0;
    for (int i = 0; i < pageNumbers; i++) {
      int k = pages.get(i);//待处理元素
      if (!cache.containsKey(k)) {
        pageMissingTimes++;//不能直接找到次数加1
        if (cache.size() < cacheSize) {//如果没有装满
          int temp = cache.size();
          cache.put(k, temp);
          linkedlist.add(k);//添加位置
        } else {
          int temp = linkedlist.get(0);
          int c = cache.get(temp);//位置
          cache.remove(temp);
          cache.put(k, c);
          linkedlist.remove(0);
          linkedlist.add(k);
        }
      } else//如果包含这个值，把这个值拿走并在后面压入
      {
        int d = linkedlist.indexOf(k);//查找存在位置
        linkedlist.remove(d);
        linkedlist.add(k);
      }
    }
    System.out.println("误码率为:" + pageMissingTimes * 1.0 / pageNumbers);
  }

  public static void main(String args[]) {
    PageCache p = new PageCache();
  }

}

/*
请输入用户访问页地址走向
1 2 3 4 1 2 3 4 8 9 4 1 2 3
请输入内储叶框数量
4
 */