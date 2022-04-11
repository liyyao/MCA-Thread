package basic.state;

import util.SleepHelper;

/**
 * interrupt() 与 interrupted()
 */
public class T05_Interrupt_and_interrupted {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for(;;) {
                if (Thread.interrupted()) {
                    System.out.println("Thread is interrupted!");
                    System.out.println(Thread.interrupted());
                }
            }
        });

        t.start();
        SleepHelper.sleepSeconds(2);
        t.interrupt();

        System.out.println("main: " + t.interrupted());     //主线程
    }
}
