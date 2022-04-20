package basic.questions;

import util.SleepHelper;

import java.util.List;
import java.util.Vector;

/**
 * 写一个固定量同步容器，拥有put和get方法，以及getCount方法，能够支持2个生产者线程以及10个消费者线程的阻塞调用
 */
public class T03_ProducerAndConsumer {

    private final int size;
    private List<Object> list;

    public T03_ProducerAndConsumer(int size) {
        this.size = size;
        list = new Vector<>(size);
    }

    public void put(Object obj) {
        list.add(obj);
    }

    public Object get() {
        return list.remove(0);
    }

    public int getCount() {
        return list.size();
    }

    public static void main(String[] args) {
        T03_ProducerAndConsumer c = new T03_ProducerAndConsumer(20);
        Object lock = new Object();
        Thread[] producerThreads = new Thread[2];
        for (int i = 0; i < producerThreads.length; i++) {
            producerThreads[i] = new Thread(() -> {
                while (true) {
                    synchronized (lock) {
                        while (c.getCount() == c.size) {
                            try {
                                System.out.println("容器满了," + Thread.currentThread().getName() + " 停止增加");
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        c.put(c.getCount() + 1);
                        System.out.println(Thread.currentThread().getName() + " 增加第 " + c.getCount() + " 个");
                        lock.notify();
                    }
                }
            }, "producer" + i);
        }

        Thread[] consumerThreads = new Thread[10];
        for (int i = 0; i < consumerThreads.length; i++) {
            consumerThreads[i] = new Thread(() -> {
                while (true) {
                    synchronized (lock) {
                        while (c.getCount() == 0) {
                            try {
                                System.out.println("容器空了，" + Thread.currentThread().getName() + " 停止取出");
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + " 取出第 " + c.get() + " 个");
                        lock.notify();
                    }
                    SleepHelper.sleepSeconds(1);
                }
            }, "consumer" + i);
        }

        for (Thread t : producerThreads) {
            t.start();
        }
        for (Thread t : consumerThreads) {
            t.start();
        }
    }
}
