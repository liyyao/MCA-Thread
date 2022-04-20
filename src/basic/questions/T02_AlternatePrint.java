package basic.questions;

/**
 * 题目2：
 * 写两个线程，A线程打印A~Z，B线程打印1~26，要求用线程顺序打印A1B2C3...Z26
 */
public class T02_AlternatePrint {

    public static void main(String[] args) {
        //CountDownLatch latch = new CountDownLatch(1);
        Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                for (int i = 1; i <= 26; i++) {
                    try {
                        //latch.countDown();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print(i);
                    lock.notify();
                }
            }
        }, "t2").start();

        new Thread(()-> {
            /*try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            synchronized (lock) {
                for (int i = 0; i < 26; i++) {
                    System.out.print((char) ('A' + i));
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();

    }
}
