package basic;

import java.util.concurrent.CountDownLatch;

/**
 * 如果要修改的两个数据在一个缓存行中，然后两个线程分别修改两个数据，那么一个线程修改数据后需要通知另一个线程数据修改了要拿新的数据
 * 如果要修改的两个数据不在一个缓存行中，两个线程分别修改两个数据，不会有线程之间的通信，可能会提高效率
 */
public class T03_CacheLinePadding {

    public static long COUNT = 10_0000_0000L;

    private static class T {
        //private long p1, p2, p3, p4, p5, p6, p7;
        public long x = 0L;
        //private long p9, p10, p11, p12, p13, p14, p15;
    }

    public static T[] arr = new T[2];

    static {
        arr[0] = new T();
        arr[1] = new T();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                arr[0].x = i;
            }
            latch.countDown();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                arr[1].x = i;
            }
            latch.countDown();
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();
        latch.await();
        System.out.println((System.nanoTime() - start) / 100_0000);
    }
}
