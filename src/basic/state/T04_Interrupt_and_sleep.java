package basic.state;

import util.SleepHelper;

/**
 * interrupt 与sleep() wait() join()
 * 当调用sleep()时，再调用interrupt，sleep()会抛出InterruptedException异常
 */
public class T04_Interrupt_and_sleep {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("Thread is interrupted!");
                System.out.println(Thread.currentThread().isInterrupted()); //抛出InterruptedException后，会自动将interrupt标志位复位 所以这里是false
            }
        });

        t.start();
        SleepHelper.sleepSeconds(5);
        t.interrupt();      //虽然设置了t线程标志位被打断了，但最终处理还是当前线程处理
    }
}
