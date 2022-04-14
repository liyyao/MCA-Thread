package basic.c_03_atomicity;

import java.util.concurrent.CountDownLatch;

public class T01_IPlusPlus {

    private static long n = 0L;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    synchronized (T01_IPlusPlus.class) {
                        n++;
                    }
                }
                latch.countDown();
            });
        }

        for (Thread t : threads) {
            t.start();
        }

        latch.await();
        System.out.println(n);
    }
}
