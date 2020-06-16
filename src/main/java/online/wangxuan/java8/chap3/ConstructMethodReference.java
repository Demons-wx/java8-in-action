package online.wangxuan.java8.chap3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author wangxuan
 * @date 2018/10/28 12:48 PM
 */

public class ConstructMethodReference {

    public static void main(String[] args) {
        // 无参的构造函数 等同于 Supplier<Apple> c11 = () -> new Apple();
        Supplier<Apple> c1 = Apple::new;
        Apple a1 = c1.get();

        // 包含一个参数的构造函数 等同于 Function<Integer, Apple> c21 = (weight) -> new Apple(weight);
        Function<Integer, Apple> c2 = Apple::new;
        Apple a2 = c2.apply(110);

        // 使用一个由Integer构成的List中的每个元素都通过下面的map方法传递给Apple的构造函数，得到一个具有不同重量苹果的List
        List<Integer> weights = Arrays.asList(7, 3, 45, 10);
        List<Apple> apples = map(weights, Apple::new);
        apples.forEach(System.out::println);

        // 一个具有两个参数的构造函数
        BiFunction<Integer, String, Apple> c3 = Apple::new;
        Apple a3 = c3.apply(150, "red");
        System.out.println(a3);

        // 自定义具有三个参数的函数式接口
        TriFunction<Integer, String, String, Apple> c4 = Apple::new;
        Apple a4 = c4.apply(160, "red", "日本");
        System.out.println(a4);
    }

    public static List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
        List<Apple> result = new ArrayList<>();
        for (Integer e : list) {
            result.add(f.apply(e));
        }
        return result;
    }

    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Apple {

        private Integer wright;
        private String color;
        private String supplier;

        public Apple(Integer wright) {
            this.wright = wright;
            this.color = "green";
            this.supplier = "中国";
        }

        public Apple(Integer wright, String color) {
            this.wright = wright;
            this.color = color;
            this.supplier = "中国";
        }
    }
}
