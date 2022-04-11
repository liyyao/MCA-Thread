package basic.c_01_volatile;

import util.SleepHelper;

public class T02_HelloVolatile {

    private static volatile boolean running = true;     //使用volatile

    private static void m() {
        System.out.println("m start");
        while (running) {

        }
        System.out.println("m end!");
    }

    public static void main(String[] args) {
        T02_HelloVolatile t = new T02_HelloVolatile();
        new Thread(T02_HelloVolatile::m, "t1").start();
        SleepHelper.sleepSeconds(1);

        running = false;
    }
}
