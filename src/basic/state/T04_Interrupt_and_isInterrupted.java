package basic.state;

import util.SleepHelper;

/**
 * interrupt() 与 isInterrupted()
 * 设置标志位 + 查询标志位
 */
public class T04_Interrupt_and_isInterrupted {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for(;;) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread is interrupted!");
                    System.out.println(Thread.currentThread().isInterrupted());
                    break;
                }
            }
        });

        t.start();
        SleepHelper.sleepSeconds(2);
        t.interrupt();
    }
}
