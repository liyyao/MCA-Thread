package basic.c_02_order;

import java.util.concurrent.CountDownLatch;

/**
 * 验证程序的乱序性
 * 判断 x , y 最终可能出现的结果
 * 正常分析：(1,0) (0,1) (1,1)，但运行程序后，发现出现了(0,0)的结果，意味着出现了x=b y=a先于a=1和b=1之前执行了
 * 上面分析可知 程序发生了乱序执行
 */
public class T01_Disorder {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < Long.MAX_VALUE; i++) {
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            CountDownLatch latch = new CountDownLatch(2);

            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                    latch.countDown();
                }
            });

            Thread other = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                    latch.countDown();
                }
            });

            one.start();
            other.start();
            latch.await();
            String result = "第" + i + "次（" + x + "," + y + "）";
            if (x == 0 && y == 0) {
                System.out.println(result);
                break;
            }
        }
    }
}
