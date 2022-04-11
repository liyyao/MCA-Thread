package basic.state;

import util.SleepHelper;

public class T08_Interrupt_and_sync {

    private static Object o = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (o) {
                SleepHelper.sleepSeconds(10);
            }
        });

        t1.start();

        SleepHelper.sleepSeconds(1);

        Thread t2 = new Thread(() -> {
            synchronized (o) {

            }
            System.out.println("t2 end!");
        });

        t2.start();
        SleepHelper.sleepSeconds(1);
        t2.interrupt();     //这个方法仅仅是设置标记位，所以不会影响锁竞争
    }
}
