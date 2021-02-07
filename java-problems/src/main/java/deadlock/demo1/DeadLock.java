package deadlock.demo1;

/**
 * Author by darcy
 * Date on 17-9-6 上午8:44.
 * Description:
 */
public class DeadLock implements Runnable {

  private static Object obj1 = new Object();
  private static Object obj2 = new Object();
  private int flag = 1;

  DeadLock(int flag) {
    this.flag = flag;
  }

  @Override
  public void run() {
    if (flag == 1) {
      synchronized (obj1) {
        System.out.println("Thread1 is running!");
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        synchronized (obj2) {
          System.out.println("1");
        }
      }
    }

    if (flag == 0) {
      synchronized (obj2) {
        System.out.println("Thread2 is running!");
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        synchronized (obj1) {
          System.out.println("2");

        }
      }
    }
  }

  public static void main(String[] args) {
    DeadLock t1 = new DeadLock(1);
    DeadLock t2 = new DeadLock(0);
    new Thread(t1).start();
    new Thread(t2).start();
  }
}
