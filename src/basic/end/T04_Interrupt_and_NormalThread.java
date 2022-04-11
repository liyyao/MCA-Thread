package basic.end;

import util.SleepHelper;

public class T04_Interrupt_and_NormalThread {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (!Thread.interrupted()) {

            }
            System.out.println("t1 end");
        });

        t.start();
        SleepHelper.sleepSeconds(1);
        t.interrupt();
    }
}
