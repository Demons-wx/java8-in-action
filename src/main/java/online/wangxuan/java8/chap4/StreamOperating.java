package online.wangxuan.java8.chap4;

import java.util.List;
import java.util.stream.Collectors;

import static online.wangxuan.java8.chap4.Dish.*;
/**
 * 流操作分为两大类:
 * 中间操作: 会返回另一个流，这让多个操作可以连接起来形成一个查询。重要的是，除非流水线上触发一个终端操作，
 * 否则中间操作不会执行任何处理。
 * 终端操作: 会从流的流水线生成结果。
 * @author wangxuan
 * @date 2018/11/10 1:09 PM
 */
public class StreamOperating {

    public static void main(String[] args) {
        List<String> names = menu.stream()     // 获得流
                .filter(d -> d.getCalories() > 300) // 中间操作
                .map(Dish::getName)                 // 中间操作
                .limit(3)                           // 中间操作
                .collect(Collectors.toList());      // 终端操作

        System.out.println(names);

        System.out.println("----------");

        // 调试演示
        // 有好几种优化利用了流的延迟性质:
        // 1. 尽管很多菜的热量都高于300卡路里, 但只选出了前三个。因为limit操作和一种称为短路的技巧。
        // 2. 尽管filter和map是两个独立的操作，但它们合并到同一次遍历中了。这叫循环合并。
        List<String> names2 = menu.stream()
                .filter(d -> {
                    System.out.println("filtering " + d.getName());
                    return d.getCalories() > 300;
                })
                .map(d -> {
                    System.out.println("mapping " + d.getName());
                    return d.getName();
                })
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(names2);
    }
}
