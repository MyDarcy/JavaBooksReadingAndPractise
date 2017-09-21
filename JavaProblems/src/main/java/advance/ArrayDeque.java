package advance;

import java.util.NoSuchElementException;
import java.util.Random;

/**
 * 数组实现双端队列.
 *
 * 主要是JDK中ArrayDeque的实现.
 *
 * @param <E>
 */
public class ArrayDeque<E> {

  transient Object[] elements; // non-private to simplify nested class access

  /**
   * The index of the element at the head of the deque (which is the
   * element that would be removed by remove() or pop()); or an
   * arbitrary number equal to tail if the deque is empty.
   */
  transient int head;

  /**
   * The index at which the next element would be added to the tail
   * of the deque (via addLast(E), add(E), or push(E)).
   */
  transient int tail;


  /**
   * Constructs an empty array deque with an initial capacity
   * sufficient to hold 16 elements.
   */
  public ArrayDeque() {
    elements = new Object[16];
  }


  public void addFirst(E e) {
    if (e == null)
      throw new NullPointerException();
    elements[head = (head - 1) & (elements.length - 1)] = e;
    if (head == tail)
      doubleCapacity();
  }

  public void addLast(E e) {
    if (e == null)
      throw new NullPointerException();
    elements[tail] = e;
    if ((tail = (tail + 1) & (elements.length - 1)) == head)
      doubleCapacity();
  }

  /**
   * Doubles the capacity of this deque.  Call only when full, i.e.,
   * when head and tail have wrapped around to become equal.
   */
  private void doubleCapacity() {
    assert this.head == this.tail;

    int var1 = this.head;
    int var2 = this.elements.length;
    int var3 = var2 - var1;
    int var4 = var2 << 1;
    if (var4 < 0) {
      throw new IllegalStateException("Sorry, deque too big");
    } else {
      Object[] var5 = new Object[var4];
      System.arraycopy(this.elements, var1, var5, 0, var3);
      System.arraycopy(this.elements, 0, var5, var3, var1);
      this.elements = var5;
      this.head = 0;
      this.tail = var2;
    }
  }

  public boolean offerFirst(E e) {
    addFirst(e);
    return true;
  }

  public boolean offerLast(E e) {
    addLast(e);
    return true;
  }


  public E removeFirst() {
    E x = pollFirst();
    if (x == null)
      throw new NoSuchElementException();
    return x;
  }

  public E removeLast() {
    E x = pollLast();
    if (x == null)
      throw new NoSuchElementException();
    return x;
  }

  public E pollFirst() {
    int h = head;
    @SuppressWarnings("unchecked")
    E result = (E) elements[h];
    // Element is null if deque empty
    if (result == null)
      return null;
    elements[h] = null;     // Must null out slot
    head = (h + 1) & (elements.length - 1);
    return result;
  }

  public E pollLast() {
    int t = (tail - 1) & (elements.length - 1);
    @SuppressWarnings("unchecked")
    E result = (E) elements[t];
    if (result == null)
      return null;
    elements[t] = null;
    tail = t;
    return result;
  }

  /**
   * @throws NoSuchElementException {@inheritDoc}
   */
  public E getFirst() {
    @SuppressWarnings("unchecked")
    E result = (E) elements[head];
    if (result == null)
      throw new NoSuchElementException();
    return result;
  }

  /**
   * @throws NoSuchElementException {@inheritDoc}
   */
  public E getLast() {
    @SuppressWarnings("unchecked")
    E result = (E) elements[(tail - 1) & (elements.length - 1)];
    if (result == null)
      throw new NoSuchElementException();
    return result;
  }

  @SuppressWarnings("unchecked")
  public E peekFirst() {
    // elements[head] is null if deque empty
    return (E) elements[head];
  }

  @SuppressWarnings("unchecked")
  public E peekLast() {
    return (E) elements[(tail - 1) & (elements.length - 1)];
  }

  public int size() {
    return (tail - head) & (elements.length - 1);
  }

  /**
   * Returns {@code true} if this deque contains no elements.
   */
  public boolean isEmpty() {
    return head == tail;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < elements.length; i++) {
      sb.append(elements[i] == null ? null + " " : elements[i].toString() + " ");
    }
    return sb.toString();
  }

  public static void main(String[] args) {

    for (int i = -1; i >= -10; i--) {
      System.out.println(Integer.toBinaryString(i));
    }

    System.out.println(Integer.toBinaryString(-1 & 15));

    ArrayDeque<Integer> deque = new ArrayDeque<>();
    for (int i = 0; i < 8; i++) {
      deque.addFirst(i);
      deque.addLast(i * 10);
      System.out.println(deque);
    }

    Random random = new Random();
    for (int i = 0; i < 10; i++) {
      int value = random.nextInt(2);
      if (value == 0) {
        deque.pollFirst();
      } else if (value == 1) {
        deque.pollLast();
      }

      System.out.println(deque);
    }
  }

}
