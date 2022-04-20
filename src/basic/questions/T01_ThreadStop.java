package basic.questions;

import util.SleepHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * 题目1：
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束.
 *
 * 第一种做法：给list添加volatile之后 ，但这种方法不可行，因为list是个引用对象，t1改变里面的值并没有改变引用对象，所以对t2来说还是不可见的
 *
 * 第二种做法：使用wait和notify，这里要注意的是wati会释放锁，而notify不会释放锁，而且这种做法要保证t2先执行，也就是首先让t2监听才可以
 */
public class T01_ThreadStop {

    List<Object> list = new ArrayList<>();

    public void add(Object a) {
        list.add(a);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        implementWitWaitAndNotify();
        //implementWithCountDownLatch();
        //implementWithLockSupportPark();
    }

    /**
     * 使用LockSupport的park，但和使用CountDownLatch一样，得使用两个
     */
    static Thread t1 = null;
    private static void implementWithLockSupportPark() {
        T01_ThreadStop t = new T01_ThreadStop();

        Thread t2 = new Thread(() -> {
            System.out.println("t2启动...");
            if (t.size() != 5) {
                LockSupport.park();
            }
            System.out.println("t2结束...");
            LockSupport.unpark(t1);
        }, "t2");
        t2.start();

        t1 = new Thread(() -> {
            System.out.println("t1启动...");
            for (int i = 0; i < 10; i++) {
                t.add(new Object());
                System.out.println("add " + i);
                if (t.size() == 5) {
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
            System.out.println("t1结束....");
        }, "t1");
        t1.start();
    }

    /**
     * 使用CountDownLatch实现
     */
    private static void implementWithCountDownLatch() {
        T01_ThreadStop t = new T01_ThreadStop();
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        new Thread(() -> {
            System.out.println("t2启动...");
            if (t.size() != 5) {
                try {
                    latch1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t2结束...");
            latch2.countDown();
        },"t2").start();

        SleepHelper.sleepSeconds(1);

        new Thread(() -> {
            System.out.println("t1启动...");
            for (int i = 0; i < 10; i++) {
                t.add(new Object());
                System.out.println("add " + i);
                if (t.size() == 5) {
                    latch1.countDown();
                    try {
                        latch2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("t1结束...");
        },"t1").start();
    }

    /**
     * 使用wait notify实现
     */
    private static void implementWitWaitAndNotify() {
        T01_ThreadStop t = new T01_ThreadStop();
        final Object lock = new Object();

        new Thread(() ->{
            synchronized (lock) {
                System.out.println("t2启动...");
                //if (t.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                //}
                System.out.println("t2结束...");
                lock.notify();              //这里要加notify，是因为t1线程还在wait
            }
        }, "t2").start();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t1启动...");
                for (int i = 0; i < 10; i++) {
                    t.add(new Object());
                    System.out.println("add " + i);
                    if (t.size() == 5) {
                        lock.notify();
                        try {
                            lock.wait();            //这里之所以要wait，是因为notify不会释放锁，如果不wait，上面即使notify了，
                                                    // 也会等这个线程中synchronized代码块中的程序执行完了才会释放锁，此时t2才能执行
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("t1结束...");
            }
        }, "t1").start();
    }
}
