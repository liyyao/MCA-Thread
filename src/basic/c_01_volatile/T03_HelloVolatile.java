package basic.c_01_volatile;

import util.SleepHelper;

public class T03_HelloVolatile {

    private static int n = 1;
    private static boolean running = true;  //与01相比，未加volatile关键字，但程序也停止了

    private static void m() {
        System.out.println("m start");
        while (running) {
            System.out.println("hello...");     //某些语句触发内存缓存同步刷新
        }
        System.out.println("m: n = " + n);
        System.out.println("m end!");
    }

    public static void main(String[] args) {
        T03_HelloVolatile t = new T03_HelloVolatile();
        new Thread(T03_HelloVolatile::m, "t1").start();
        SleepHelper.sleepSeconds(1);
        n = 4;
        running = false;
    }
}
