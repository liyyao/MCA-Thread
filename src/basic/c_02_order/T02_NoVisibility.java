package basic.c_02_order;

/**
 * 这个程序有两个问题：
 * 1.ready并没有用volatile关键字修饰，在主线程中将ready设置为true，t线程中的ready是不可见的
 *  MESI的主动性、Thread.yield的同步刷新使ready可见，严谨些的话就是将ready用volatile修饰
 *
 * 2.有序性，线程中打印的number可能为0，因为主程序中number = 42; ready=true这两句之间没有关系，可能会顺序重排
 *  这样可能会先将ready=true，然后t线程执行，输入number=0，然后主程序设置number = 42;
 *
 */
public class T02_NoVisibility {

    private static boolean ready = false;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new ReaderThread();
        t.start();
        number = 42;
        ready = true;
        t.join();
    }
}
