package advance;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Author by darcy
 * Date on 17-9-21 下午9:59.
 * Description:
 *
 * 实现一个栈和堆的混合数据结构, 就是支持栈的push, pop方法, popMax方法.
 */
public class StackWithHeap {

  Deque<Integer> deque = new LinkedList<>();
  Deque<Integer> auxiliary = new LinkedList<>();

  public int size() {
    return deque.size();
  }

  public void push(Integer integer) {
    deque.addLast(integer);
  }

  public Integer pop() {
    if (isEmpty()) {
      throw new RuntimeException("Empty Stack.");
    }
    return deque.removeLast();
  }

  public boolean isEmpty() {
    return deque.size() == 0;
  }


  public Integer popMax() {

    if (isEmpty()) {
      throw new RuntimeException("Empty Stack.");
    }

    Integer max = null;
    while (!isEmpty()) {
      Integer val = deque.removeLast();
      auxiliary.addLast(val);
      if (max == null || max < val) {
        max = val;
      }
    }
    int maxCount = 0;
    while (!auxiliary.isEmpty()) {
      Integer val = auxiliary.removeLast();
      if (val.equals(max)) {
        maxCount++;
        // 多余一次的max, 那么后续的max还需是入栈.
        if (maxCount >= 2) {
          deque.addLast(val);
        }
      } else {
        deque.addLast(val);
      }
    }

    return max;

  }

  public static void main(String[] args) {
    StackWithHeap mix = new StackWithHeap();
    for (int i = 0; i < 10; i++) {
      mix.push(i);
    }
    System.out.println(mix.deque);

    for (int i = 0; i < 10; i++) {
      mix.push(i);
    }
    System.out.println(mix.deque);

    System.out.println(mix.pop());
    System.out.println(mix.pop());
    System.out.println(mix.deque);
    System.out.println(mix.popMax());
    System.out.println(mix.deque);
  }
}
