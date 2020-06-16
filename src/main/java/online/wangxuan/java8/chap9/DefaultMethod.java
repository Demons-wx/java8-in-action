package online.wangxuan.java8.chap9;

import java.util.*;

/**
 * @author wangxuan
 * @date 2019/1/11 12:17 AM
 */

public class DefaultMethod {

    public static void main(String[] args) {

        // 到目前为止，你已经使用了多个默认方法。
        // 两个例子就是你前面已经见过的List接口中的sort，以及Collection接口中的stream。

        // List接口中的sort方法是Java8中全新的方法：
        //        default void sort(Comparator<? super E> c) {
        //            Collections.sort(this, c);
        //        }

        // 由于有了这个新方法，我们现在可以直接通过调用sort，对列表中的元素排序：
        List<Integer> numbers = Arrays.asList(3, 5, 1, 2, 6);
        numbers.sort(Comparator.naturalOrder());

        //***************************************
        // 默认方法的引入就是为了以兼容的方式解决像Java API这样的类库的演进问题。
        //***************************************

        // 静态方法及接口
        // 同时定义接口以及工具辅助类是Java语言常用的一种模式，工具类定义了与接口实例协作的很多
        // 静态方法。比如，Collections就是处理Collection对象的辅助类。由于静态方法可以存在于
        // 接口内部，你代码中的这些辅助类就没有了存在的必要，你可以把这些静态方法转移到接口内部
        // 为了保持后向兼容性，这些类依然会存在于Java应用程序的接口之中。

        /************** 9.1 不断演进的API **************/
        System.out.println("/************** 9.1 不断演进的API **************/");
        System.out.println();

        // 假如你是一个流行Java绘图库的设计者，你的库中包含了一个Resizable接口。现在你突然意识
        // 到Resizable接口遗漏了一些功能。

        // 初始化版本的API
        /**
         * {@link Resizable}
         * {@link Rectangle}
         * {@link Ellipse}
         * {@link Square}
         * {@link Game}
         * {@link Utils}
         */

        // 不同类型的兼容性：二进制、源代码和函数行为
        // - 二进制级的兼容性表示现有的二进制执行文件能无缝持续链接和运行。比如，为接口添加一个方法
        //   就是二进制级的兼容，这种方式下，如果新添加的方法不被调用，接口已经实现的方法可以继续运行，不会出现错误。
        // - 源代码级别的兼容性表示引入变化之后，现有的程序依然能成功编译通过。比如，向接口添加新方法
        //   就不是源码级别的兼容，因为遗留代码并没有实现新引入的方法，所以它们无法顺利通过编译。
        // - 函数行为的兼容性表示变更发生以后，程序接受同样的输入能得到同样的结果。比如，为接口添加
        //   新的方法就是函数行为兼容的，因为新添加的方法在程序中并未被调用。

        /************** 9.2 概述默认方法 **************/
        System.out.println("/************** 9.2 概述默认方法 **************/");
        System.out.println();

        // Java8 中的抽象类和抽象接口
        // 抽象类和抽象接口之间的区别是什么？它们不都能包含抽象方法和包含方法体的实现吗？
        // 首先，一个类只能继承一个抽象类，但是一个类可以实现多个接口。
        // 其次，一个抽象类可以通过实例变量(字段)保存一个通用状态，而接口是不能有实例变量的。

        //***************************************
        // 默认方法是一种以源码兼容方式向接口内添加实现的方法。
        //***************************************

        /************** 9.3 默认方法的使用模式 **************/
        System.out.println("/************** 9.3 默认方法的使用模式 **************/");
        System.out.println();

        // 可选方法

        // 你可能碰到过这种情况，类实现了接口，不过却刻意的将一些方法实现留白。
        // 以Iterator接口为例，Iterator接口定义了hasNext、next，还定义了remove方法。
        // Java8 之前由于用户通常不会使用该方法，remove方法常被忽略。
        // 在Java8中，Iterator接口就为remove方法提供了一个默认实现，如下所示：
        //        interface Iterator<T> {
        //            boolean hasNext();
        //            T next();
        //            default void remove() {
        //                throw new UnsupportedOperationException();
        //            }
        //        }

        // 通过这种方式，你可以减少无效的模板代码。实现Iterator接口的每一个类都不需要要再声明一个空的
        // remove方法了，因为它现在已经有一个默认的实现。

        // 行为的多继承

        //***************************************
        // 默认方法让之前无法想象的事以一种优雅的方式得以实现，即行为的多继承。
        //***************************************

        // 1. 类的多继承
        //        public class ArrayList<E> extends AbstractList<E>
        //                implements List<E>, RandomAccess, Cloneable,
        //                Serializable, Iterable<E>, Collection<E> {
        //
        //        }

        // ArrayList继承了一个类，实现了六个接口。因此ArrayList实际是7个类型的直接子类。

        // 2. 利用正交方法精简接口

        // 假设你需要为你正在创建的游戏定义多个具有不同特质的形状。有的需要调整大小，但是不需要旋转，有的需要
        // 旋转和移动，但是不需要调整大小。这种情况下，怎么设计才能尽可能地重用代码？
        /**
         * {@link Rotatable}
         * {@link Moveable}
         * {@link Resizable}
         */

        // 3. 组合接口

        // 通过组合这些接口，你现在可以为你的游戏创建不同的实体类。比如：
        /**
         * {@link Monster}
         * {@link Sun}
         */

        /************** 9.4 解决冲突的规则 **************/
        System.out.println("/************** 9.4 解决冲突的规则 **************/");
        System.out.println();

        // 随着默认方法在Java8中引入，有可能出现一个类继承了多个方法而它们使用的却是相同的函数签名。
        // 这种情况下，类会选择使用哪一个函数？

        /**
         * {@link C}
         */

        // 1. 解决问题的三条规则
        // 如果一个类使用相同的函数签名从多个地方继承了方法，通过三条规则可以进行判断。
        // (1) 类中方法的优先级最高。类或父类中声明的方法的优先级高于任何声明为默认方法的优先级。
        // (2) 如果无法依据第一条进行判断，那么子接口的优先级更高：函数签名相同时，优先选择拥有最具体实现的默认方法的接口，
        //     即如果B继承了A，那么B就比A更加具体。
        // (3) 最后，如果还是无法判断，继承了多个接口的类必须通过显式覆盖和调用期望的方法，显式的选择使用哪一个默认方法的实现。

        // 3. 如何显式的消除歧义

        //        public class C implements B, A {
        //            void hello() {
        //                显式的选择调用接口B中的方法
        //                B.super.hello();
        //            }
        //        }


    }

}

interface Rotatable {
    void setRotationAngle(int angleInDegrees);
    int getRotationAngle();
    default void rotateBy(int angleInDegrees) {
        setRotationAngle((getRotationAngle() + angleInDegrees) % 360);
    }
}

interface Moveable {
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
    default void moveHorizontally(int distance) {
        setX(getX() + distance);
    }
    default void moveVertically(int distance) {
        setY(getY() + distance);
    }
}

class Monster implements Rotatable, Moveable, Resizable {
    @Override
    public void draw() {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setWidth(int width) {

    }

    @Override
    public void setHeight(int height) {

    }

    @Override
    public void setAbsoluteSize(int width, int height) {

    }

    @Override
    public void setRotationAngle(int angleInDegrees) {

    }

    @Override
    public int getRotationAngle() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setX(int x) {

    }

    @Override
    public void setY(int y) {

    }
}

class Sun implements Moveable, Rotatable {
    @Override
    public void setRotationAngle(int angleInDegrees) {

    }

    @Override
    public int getRotationAngle() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setX(int x) {

    }

    @Override
    public void setY(int y) {

    }
}



















