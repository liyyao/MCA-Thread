package basic.c_03_atomicity;

import util.SleepHelper;

/**
 * 不加锁就是并发操作
 * 加锁后就是序列化操作
 */
public class T02_WhatIsLock {

    private static Object o = new Object();

    public static void main(String[] args) {
        Runnable r = () -> {
            //synchronized (o) {
                System.out.println(Thread.currentThread().getName() + " start!");
                SleepHelper.sleepSeconds(2);
                System.out.println(Thread.currentThread().getName() + " end!");
            //}
        };

        for (int i = 0; i < 3; i++) {
            new Thread(r).start();
        }
    }
}
