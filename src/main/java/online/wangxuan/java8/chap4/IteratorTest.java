package online.wangxuan.java8.chap4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 内部迭代的优势
 * 项目可以透明的并行处理，或者用更优化的顺序进行处理。
 * @author wangxuan
 * @date 2018/11/4 10:55 PM
 */

public class IteratorTest {

    /**
     * 集合: 用for-each循环外部迭代
     */
    public static void outsideIterator() {
        List<String> names = new ArrayList<>();
        for (Dish menu : Dish.menu) {
            names.add(menu.getName());
        }
    }

    /**
     * 集合: 用背后的迭代器做外部迭代
     */
    public static void originalIterator() {
        List<String> names = new ArrayList<>();
        Iterator<Dish> iterator = Dish.menu.iterator();
        while (iterator.hasNext()) {
            Dish d = iterator.next();
            names.add(d.getName());
        }
    }

    /**
     * 流: 内部迭代
     */
    public static void insideIterator() {
        List<String> names = Dish.menu.stream()
                .map(Dish::getName).collect(Collectors.toList());
    }
}
