package ch4;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author by darcy
 * Date on 17-6-16 下午3:32.
 * Description:
 */
public class TestGC {
    public static final int SIZE = 512 * 1024;
    public static final ConcurrentSkipListSet<BigSpace> set = new ConcurrentSkipListSet<>();

    public static class BigSpace implements Comparable {
        byte[] bytes;
        BigSpace() {
            bytes = new byte[SIZE];
        }

        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }

    public static class Task implements Runnable {
        int id;
        public Task(int id) {
            this.id = id;
        }
        @Override
        public void run() {
            while (true) {
                BigSpace big = new BigSpace();
                try {
                    Thread.sleep(400);
                    System.out.println(Thread.currentThread().getName() + "sleep.");
                    // 这部分不会进行垃圾回收,那么就可以看到进入老年代的情况.
                    if (id % 8 == 0) {
                        set.add(big);
                    } else {
                        big = null;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(40);

        for (int i = 0; i < 40; i++) {
            es.execute(new Task(i));
        }

        Thread.sleep(1000 * 60);
        es.shutdown();
        System.out.println("finish.");
    }
}
