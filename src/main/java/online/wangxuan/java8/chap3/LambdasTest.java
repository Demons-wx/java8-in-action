package online.wangxuan.java8.chap3;

import online.wangxuan.java8.chap1.FilteringApples;

import java.util.Comparator;
import java.util.function.*;

/**
 * @author wangxuan
 * @date 2018/10/27 9:39 PM
 */

public class LambdasTest {

    public static void main(String[] args) {
        Predicate<Integer> oddNumbers = (Integer i) -> i % 2 == 1;

        Predicate<Integer> oddNumbers2 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer % 2 == 0;
            }
        };

        IntPredicate evenNumbers = (int i) -> i % 2 == 0;

        System.out.println(evenNumbers.test(1000));
        System.out.println(oddNumbers.test(1000));

        // 创建一个对象
        Supplier<FilteringApples.Apple> appleSupplier = () -> new FilteringApples.Apple(100, "red");
        // 消费一个对象
        Consumer<FilteringApples.Apple> appleConsumer = (FilteringApples.Apple a) -> System.out.println(a.getColor());
        appleConsumer.accept(appleSupplier.get());

        // 从一个对象中选择/提取
        // 方式1
        ToIntFunction<String> stringToInt = String::length;
        System.out.println(stringToInt.applyAsInt("apple"));
        // 方式2
        Function<String, Integer> strToInt = String::length;
        System.out.println(strToInt.apply("apple"));

        // 合并两个值
        IntBinaryOperator merge = (int a, int b) -> a * b;
        System.out.println(merge.applyAsInt(10, 8));

        // 比较两个对象
        Comparator<FilteringApples.Apple> comparator1 = (FilteringApples.Apple a1, FilteringApples.Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
        Comparator<FilteringApples.Apple> comparator2 = Comparator.comparing(FilteringApples.Apple::getWeight);
        BiFunction<FilteringApples.Apple, FilteringApples.Apple, Integer> comparator3 = (FilteringApples.Apple a1, FilteringApples.Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
        ToIntBiFunction<FilteringApples.Apple, FilteringApples.Apple> comparator4 = (FilteringApples.Apple a1, FilteringApples.Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
        FilteringApples.Apple a1 = new FilteringApples.Apple(150, "red");
        FilteringApples.Apple a2 = new FilteringApples.Apple(160, "green");
        System.out.println(comparator1.compare(a1, a2));
        System.out.println(comparator2.compare(a1, a2));
        System.out.println(comparator3.apply(a1, a2));
        System.out.println(comparator4.applyAsInt(a1, a2));

        // 函数复合 Function接口提供了andThen和compose两个默认方法，它们都会返回Function的一个实例
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g); // g(f(x))
        int result = h.apply(1);
        System.out.println(result);

        Function<Integer, Integer> h1 = f.compose(g); // f(g(x))
        System.out.println(h1.apply(1));
    }
}
