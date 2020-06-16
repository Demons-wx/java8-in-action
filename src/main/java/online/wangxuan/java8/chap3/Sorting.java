package online.wangxuan.java8.chap3;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author wangxuan
 * @date 2018/10/28 10:03 PM
 */

public class Sorting {

    public static void main(String[] args) {
        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red")));

        // 1 传递代码
        inventory.sort(new AppleComparator());

        // 2 使用匿名类
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });

        // 3. 使用Lambda表达式
        inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        // Comparator<Apple> c = Comparator.comparing(a -> a.getWeight());
        // inventory.sort(c);
        inventory.sort(Comparator.comparing(a -> a.getWeight()));

        // 4. 使用方法引用
        inventory.sort(Comparator.comparing(Apple::getWeight));
    }

    @Data
    @AllArgsConstructor
    public static class Apple {
        private Integer weight = 0;
        private String color = "";
    }

    public static class AppleComparator implements Comparator<Apple> {
        @Override
        public int compare(Apple a1, Apple a2) {
            return a1.getWeight().compareTo(a2.getWeight());
        }
    }
}
