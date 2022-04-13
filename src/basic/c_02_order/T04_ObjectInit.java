package basic.c_02_order;

/**
 * 对象创建过程
 *
 * 0 new #2 <java/lang/Object>      //申请一块内存空间，对象多大就占多大内存空间，其中成员变量的初始值都是默认值，这里就是半初始化状态
 * 3 dup
 * 4 invokespecial #1 <java/lang/Object.<init> : ()V>       //调用构造方法进行值的初始化
 * 7 astore_1           //对象引用与内存对象建立关联
 * 8 return
 */
public class T04_ObjectInit {

    public static void main(String[] args) {
        Object o = new Object();
    }
}
