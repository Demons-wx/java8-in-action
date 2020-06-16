package online.wangxuan.java8.chap9;

/**
 * @author wangxuan
 * @date 2019/1/13 9:05 PM
 */

public class C implements B, A {
    public static void main(String[] args) {
        new C().hello();
    }
}

interface A {
    default void hello() {
        System.out.println("Hello from A");
    }
}

interface B extends A {
    default void hello() {
        System.out.println("Hello from B");
    }
}