package basic.c_02_order;

import java.io.IOException;

/**
 * this对象逸出
 * 这个程序可能会输出num = 0;
 *
 * 因为指令重排后，可能会在初始化时，先将当前对象的引用与内存对象进行了关联，然后调用构造方法，在调用构造方法的时候发现new了一个线程并打印了num，
 * 这里num还未初始化，就可能会打印出0
 *
 * 所以不要在构造方法里面启动线程方法，在单独的方法里进行启动
 */
public class T03_ThisEscape {

    private int num = 8;

    public T03_ThisEscape() {
        new Thread(() -> System.out.println(this.num)).start();
    }

    public static void main(String[] args) throws IOException {
        new T03_ThisEscape();
        System.in.read();
    }
}
