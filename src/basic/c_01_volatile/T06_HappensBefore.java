package basic.c_01_volatile;

import java.util.concurrent.CountDownLatch;

/**
 * happens-before规则：
 *
 */
public class T06_HappensBefore {

    private static int n = 0;

    public static void main(String[] args) throws InterruptedException {
        //startRule();
        joinRule();
    }

    /**
     * start()规则：如果线程A执行操作ThreadB.start()（启动线程B），那么A线程的ThreadB.start()操作happens-before于线程B中的任意操作
     * 1 happens-before 2由程序顺序规则产生
     * 2 happens-before 4由start()规则产生
     * 根据传递性，将有1 happens-before 4，这意味着线程t1在执行t2.start()之前对共享变量所做的修改，接下来在线程t2开始执行后都将确保对线程t2可见
     * 所以t2线程讲到的n = 1；
     */
    private static void startRule() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        Thread t2 = new Thread(() -> {              //3.线程t2开始执行
            System.out.println("t2 start");
            System.out.println("t2: n = " + n);     //4.线程t2读共享变量n
            System.out.println("t2 end");
            latch.countDown();
        });

        Thread t1 = new Thread (() -> {
            System.out.println("t1 start");
            n = 1;              //1.线程t1修改共享变量n
            t2.start();         //2.线程t1执行线程t2.start()
            System.out.println("t1 end");
            latch.countDown();
        });

        t1.start();

        latch.await();
        System.out.println("结束...");
    }


    /**
     * joint()规则：如果线程A执行操作ThreadB.join()并成功返回，那么线程B中的任意操作happens-before于线程A从ThreadB。join()操作成功返回。
     * 2 happens-before 4 由于join()规则产生
     * 4 happens-before 5由程序顺序规则产生
     * 根据传递性规则，将有2 happens-before 5，这意味着线程t1执行操作t2.join()并成功返回后，线程t2中的任意操作都将对线程t1可见
     * @throws InterruptedException
     */
    private static void joinRule() throws InterruptedException {
        Thread t2 = new Thread(() -> {
            System.out.println("t2 start");
            n = 6;                                  //2.线程t2写共享变量n = 6;
            System.out.println("t2 end");
        });                                         //3.t2线程终止

        Thread t1 = new Thread(() -> {
            System.out.println("t1 start");
            try {
                t2.join();                          //1.线程t1执行：t2.join()
            } catch (InterruptedException e) {
                e.printStackTrace();
            }                                       //4.线程t1从t2.join()操作成功返回
            System.out.println("t1: n = " + n);     //5.线程t1读共享变量n = 6
            System.out.println("t1 end");
        });

        t1.start();

        t2.start();

        t1.join();
        System.out.println("end....");
    }

}
