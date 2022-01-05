package com.darcy;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hezhiqiang05
 * @date 2022/01/04
 * description:
 * https://segmentfault.com/a/1190000039815066
 *
 */
public class ThreadPoolSyncDemo {

    public static void main(String[] args) throws InterruptedException {
        test();
    }

    public static void test() throws InterruptedException {

        ThreadPoolExecutor executorService = new ThreadPoolExecutor(2, 10, 30, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new MyThreadFactory("test"), new ThreadPoolExecutor.DiscardPolicy());

        //每隔两秒打印线程池的信息
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("=====================================thread-pool-info:" + new Date() + "=====================================");
            System.out.println("CorePoolSize:" + executorService.getCorePoolSize());
            System.out.println("PoolSize:" + executorService.getPoolSize());
            System.out.println("ActiveCount:" + executorService.getActiveCount());
            System.out.println("KeepAliveTime:" + executorService.getKeepAliveTime(TimeUnit.SECONDS));
            System.out.println("QueueSize:" + executorService.getQueue().size());
        }, 0, 2, TimeUnit.SECONDS);

        try {
            //同时提交5个任务,模拟达到最大线程数
            for (int i = 0; i < 10; i++) {
                executorService.execute(new Task());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //休眠10秒，打印日志，观察线程池状态
        Thread.sleep(10000);

        //每隔3秒提交一个任务，每个任务执行耗时1s
        while (true) {
            Thread.sleep(1000);
            executorService.submit(new Task());
            executorService.submit(new Task());
            executorService.submit(new Task());
        }
    }

    static class MyThreadFactory implements ThreadFactory {
        static final AtomicInteger threadNumber = new AtomicInteger(0);
        final ThreadGroup group;
        final String namePrefix;

        public MyThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix + "-thread-";
            this.group = new ThreadGroup(namePrefix);
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(group, r, namePrefix + threadNumber.incrementAndGet(), 0);
            return thread;
        }
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "-执行任务");
        }
    }
}
/*
=====================================thread-pool-info:Tue Jan 04 20:05:42 CST 2022=====================================
CorePoolSize:2
PoolSize:3
ActiveCount:3
KeepAliveTime:30
QueueSize:2
Thread[test-thread-3,5,test]-执行任务
Thread[test-thread-2,5,test]-执行任务
Thread[test-thread-1,5,test]-执行任务
=====================================thread-pool-info:Tue Jan 04 20:05:44 CST 2022=====================================
CorePoolSize:2
PoolSize:3
ActiveCount:2
KeepAliveTime:30
QueueSize:0
Thread[test-thread-3,5,test]-执行任务
Thread[test-thread-2,5,test]-执行任务
=====================================thread-pool-info:Tue Jan 04 20:05:46 CST 2022=====================================
CorePoolSize:2
PoolSize:3
ActiveCount:0
KeepAliveTime:30
QueueSize:0
=====================================thread-pool-info:Tue Jan 04 20:05:48 CST 2022=====================================
CorePoolSize:2
PoolSize:3
ActiveCount:0
KeepAliveTime:30
QueueSize:0
=====================================thread-pool-info:Tue Jan 04 20:05:50 CST 2022=====================================
CorePoolSize:2
PoolSize:3
ActiveCount:0
KeepAliveTime:30
QueueSize:0
=====================================thread-pool-info:Tue Jan 04 20:05:52 CST 2022=====================================
CorePoolSize:2
PoolSize:3
ActiveCount:0
KeepAliveTime:30
QueueSize:0
=====================================thread-pool-info:Tue Jan 04 20:05:54 CST 2022=====================================
CorePoolSize:2
PoolSize:3
ActiveCount:0
KeepAliveTime:30
QueueSize:0
=====================================thread-pool-info:Tue Jan 04 20:05:56 CST 2022=====================================
CorePoolSize:2
PoolSize:3
ActiveCount:1
KeepAliveTime:30
QueueSize:0
Thread[test-thread-1,5,test]-执行任务
=====================================thread-pool-info:Tue Jan 04 20:05:58 CST 2022=====================================
CorePoolSize:2
PoolSize:3
ActiveCount:0
KeepAliveTime:30
QueueSize:0
Thread[test-thread-3,5,test]-执行任务
=====================================thread-pool-info:Tue Jan 04 20:06:00 CST 2022=====================================
CorePoolSize:2
PoolSize:3
ActiveCount:0
KeepAliveTime:30
QueueSize:0
=====================================thread-pool-info:Tue Jan 04 20:06:02 CST 2022=====================================
CorePoolSize:2
PoolSize:3
ActiveCount:1
KeepAliveTime:30
QueueSize:0
Thread[test-thread-2,5,test]-执行任务
=====================================thread-pool-info:Tue Jan 04 20:06:04 CST 2022=====================================
CorePoolSize:2
PoolSize:3
ActiveCount:0
KeepAliveTime:30
QueueSize:0
Thread[test-thread-1,5,test]-执行任务
 */
