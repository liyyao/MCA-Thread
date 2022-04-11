package basic.c_01_volatile;

import util.SleepHelper;

public class T01_HelloVolatile {

    private static boolean running = true;

    private static void m() {
        System.out.println("m start");
        while (running) {

        }
        System.out.println("m end!");
    }

    public static void main(String[] args) {
        T01_HelloVolatile t = new T01_HelloVolatile();
        new Thread(T01_HelloVolatile::m, "t1").start();
        SleepHelper.sleepSeconds(1);

        running = false;
    }
}
