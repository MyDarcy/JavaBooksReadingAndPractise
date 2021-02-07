package ch6;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author by darcy
 * Date on 17-6-16 下午8:34.
 * Description:
 */
public class CallCallerTest {
    public static final Map<String, Integer> map
            = Collections.synchronizedMap(new HashMap<>());
    public static final Random random = new Random(31);
    public static final int BOUND = 100000000;
    public static class Task implements Runnable {

        @Override
        public void run() {
            while (true) {
                int random = CallCallerTest.random.nextInt(BOUND);
                map.put(Thread.currentThread().getName() + random, random);
            }
        }
    }

    public static final Map<String, Integer> cmap
            = new ConcurrentHashMap<>();
    public static class Task2 implements Runnable {
        public static final ThreadLocal<Random> local
                = new ThreadLocal<Random>(){

            @Override
            protected Random initialValue() {
                return new Random();
            }
        };
        @Override
        public void run() {
            while (true) {
                int random = local.get().nextInt(BOUND);
                cmap.put(Thread.currentThread().getName() + random, random);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 8; i++) {
            es.execute(new Task()); // 2159430
//            es.execute(new Task2()); // 2696968
        }

        Thread.sleep(1000 * 5);
        es.shutdownNow();
//        System.out.println(cmap.size());
        System.out.println(map.size());
    }


}

