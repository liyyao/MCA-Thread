package basic.c_01_volatile;

/**
 * volatile不能保证语句的原子性
 */
public class T05_VolatileAtomic {

    private static volatile int n = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[100];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    n += 1;
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        System.out.println("n = " + n);
    }
}
