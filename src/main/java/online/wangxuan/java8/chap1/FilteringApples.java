package online.wangxuan.java8.chap1;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author wangxuan
 * @date 2018/10/27 12:49 PM
 */

public class FilteringApples {

    public static void main(String[] args) {

        List<Apple> inventory = Arrays.asList(new Apple(80, "green"),
                                              new Apple(155, "green"),
                                              new Apple(120, "red"));

        // 原始方法筛选绿色苹果
        List<Apple> greenApples = filterGreenApples(inventory);
        System.out.println(greenApples);
        // 原始方法筛选重的苹果
        List<Apple> heavyApples = filterHeavyApples(inventory);
        System.out.println(heavyApples);

        // 传递代码, 使用方法引用在Java代码里传递方法
        List<Apple> greenApples1 = filterApples(inventory, new Predicate<Apple>() {
            @Override
            public boolean test(Apple apple) {
                return FilteringApples.isGreenApple(apple);
            }
        });
        List<Apple> greenApples2 = filterApples(inventory, FilteringApples::isGreenApple);
        List<Apple> heavyApples2 = filterApples(inventory, FilteringApples::isHeavyApple);

        // 从传递方法到lambda
        List<Apple> greenApples3 = filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
        List<Apple> heavyApples3 = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
        List<Apple> weiredApples = filterApples(inventory, (Apple a) -> a.getWeight() < 80 || "brown".equals(a.getColor()));

        // 使用通用库提供的方法，甚至都不用写filterApples了
        // 顺序处理
        List<Apple> heavyApples4 = inventory.stream().filter((Apple a) -> a.getWeight() > 150).collect(Collectors.toList());
        System.out.println(heavyApples4);
        // 并行处理
        List<Apple> heavyApples5 = inventory.parallelStream().filter((Apple a) -> a.getWeight() > 150).collect(Collectors.toList());
        System.out.println(heavyApples5);
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }

        return result;
    }

    public static List<Apple> filterHeavyApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > 150) {
                result.add(apple);
            }
        }

        return result;
    }

    public static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor());
    }

    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }

    @Data
    public static class Apple {

        private Integer weight = 0;
        private String color = "";

        public Apple(Integer weight, String color) {
            this.weight = weight;
            this.color = color;
        }
    }
}
