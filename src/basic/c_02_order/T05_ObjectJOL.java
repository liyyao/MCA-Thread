package basic.c_02_order;

import org.openjdk.jol.info.ClassLayout;

/**
 * https://mvnrepository.com/artifact/org.openjdk.jol/jol-core
 * <dependency>
 *     <groupId>org.openjdk.jol</groupId>
 *     <artifactId>jol-core</artifactId>
 *     <version>0.16</version>
 *     <scope>provided</scope>
 * </dependency>
 *
 */
public class T05_ObjectJOL {

    private static class T {
        int m = 8;
    }

    public static void main(String[] args) {
        T o = new T();
        String s = ClassLayout.parseInstance(o).toPrintable();
        System.out.println(s);
    }
}
