package basic;

import java.util.concurrent.*;

/**
 * 多线程的实现方式
 */
public class T02_HowToCreateThread {

    /**
     * 方式一：继承Thread类
     */
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello MyThread!");
        }
    }

    /**
     * 方式二：实现Runnable接口
     * 较方式一要好，因为实现接口后还可以继承其他类，更灵活
     */
    static class MyRun implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello MyRun!");
        }
    }

    static class MyCall implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("Hello MyCall!");
            return "success";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //方式一：继承Thread类
        new MyThread().start();

        //方式二：实现Runnable接口
        new Thread(new MyRun()).start();

        //方式三：Lambda
        new Thread(() -> {
                System.out.println("Hello Lambda!");
        }).start();

        //方式四：线程池
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            System.out.println("Hello ThreadPool");
        });
        service.shutdown();

        //方式五：实现Callable接口
        FutureTask<String> task = new FutureTask<>(new MyCall());
        Thread t = new Thread(task);
        t.start();
        System.out.println(task.get());

        ExecutorService service2 = Executors.newCachedThreadPool();
        Future<String> f = service2.submit(new MyCall());
        String s = f.get();
        System.out.println(s);
        service2.shutdown();
    }
}
