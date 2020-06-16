package online.wangxuan.java8.chap6;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.*;

/**
 * 前面，我们用Collectors类提供的一个方便的工厂方法创建了一个收集器，它将n个自然数划分为质数和非质数。
 * 我们通过限制除数不超过被测试数的平方根，对最初的isPrime方法做了一些改进。
 * 还有没有办法获得更好的性能？
 *
 * <p>仅用质数做除数</p>
 *
 *
 * @author wangxuan
 * @date 2018/11/17 7:42 PM
 */

public class PrimeNumbersCollector implements Collector<Integer,
        Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

    /**
     * 第一步: 实现归约过程
     * @return
     */
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {{
            put(true, new ArrayList<>());
            put(false, new ArrayList<>());
        }};
    }

    /**
     * 收集器中最重要的方法是accumulator,因为它定义了如何收集流中元素的逻辑。
     * 现在在任何一次迭代中，都可以访问收集过程的部分结果，也就是包含迄今找到的质数的累加器
     * @return
     */
    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
            // 根据isPrime的结果，获取质数或非质数列表
            acc.get(CollectorsTest.isPrime(acc.get(true), candidate))
                    .add(candidate);
        };
    }

    /**
     * 第二步：让收集器并行工作(如果可能)
     * @return
     */
    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));

            return map1;
        };
    }

    /**
     * 第四步：finisher方法和收集器的characteristics方法
     * @return
     */
    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
    }
}





















