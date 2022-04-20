package basic.questions;

import util.SleepHelper;

import java.util.LinkedList;

/**
 * 写一个固定量同步容器，拥有put和get方法，以及getCount方法，能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * 背过
 *
 * 但这种方法有个问题，就是生产者和消费者都阻塞在一个队列里，生产者唤醒时其实只需要唤醒消费者，但在一个阻塞队列里就会全部唤醒
 */
public class T04_ProducerAndConsumer<T> {

    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;

    public synchronized void put(T t) {
        while (lists.size() == MAX) {       //这里用while而不用if是避免虚假唤醒线程而产生问题
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lists.add(t);
        this.notifyAll();;
    }

    public synchronized T get() {
        T t = null;
        while (lists.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = lists.removeFirst();
        this.notifyAll();
        return t;
    }

    public static void main(String[] args) {
        T04_ProducerAndConsumer<String> c = new T04_ProducerAndConsumer<>();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(c.get());
                }
            }, "c" + i).start();
        }

        SleepHelper.sleepSeconds(2);

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    c.put(Thread.currentThread().getName() + " " + j);
                }
            }, "p" + i).start();
        }
    }
}
