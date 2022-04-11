package basic;

import sun.misc.Contended;

import java.util.concurrent.CountDownLatch;

/**
 * 如果要修改的两个数据在一个缓存行中，然后两个线程分别修改两个数据，那么一个线程修改数据后需要通知另一个线程数据修改了要拿新的数据
 * 如果要修改的两个数据不在一个缓存行中，两个线程分别修改两个数据，不会有线程之间的通信，可能会提高效率
 *
 * 之前是自己填充的字节，但如果将来缓行的大小不是64了，而是128等，还要修改代码么？
 * java在1.8提供了一个注解：Contended，可以保证被标注的数据和其他数据不会在同一个缓存行上
 * 但在运行程序的时候，需要将JVM的一个参数进行设置：-XX:-RestrictContended
 * 1.9之后这个注解也不起作用了
 */
public class T04_CacheLineContended {

    public static long COUNT = 10_0000_0000L;

    private static class T {
        @Contended
        public long x = 0L;
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
