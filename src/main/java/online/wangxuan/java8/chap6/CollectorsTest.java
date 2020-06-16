package online.wangxuan.java8.chap6;

import online.wangxuan.java8.chap4.Dish;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;
import static online.wangxuan.java8.chap4.Dish.*;
import static java.util.stream.Collector.Characteristics.*;

/**
 * @author wangxuan
 * @date 2018/11/17 11:45 AM
 */
public class CollectorsTest {

    public static void main(String[] args) {

        /*********** 归约和汇总 ************/
        System.out.println("/*********** 归约和汇总 ************/");

        // 利用counting，数一数菜单里有多少种菜
        long howManyDishes = menu.stream().collect(counting());
        // 还可以写的更为直接:
        howManyDishes = menu.stream().count();

        // 查找流中的最大值和最小值
        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = menu.stream()
                .collect(maxBy(dishCaloriesComparator));


        // 汇总
        // Collectors类专门为汇总提供了一个工厂方法: Collectors.summingInt:
        // 你可以这样求出菜单列表的总热量:
        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));

        // Collectors.averagingInt averagingLong averagingDouble可以计算数值的平均数:
        double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));

        // summarizingInt工厂方法可以收集到更多的结果。
        // 例如，通过一次summarizing操作你就可以数出菜单中元素的个数，并得到菜肴热量总和，平均值、最大值和最小值：
        IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(menuStatistics);


        // 连接字符串
        // joining工厂方法返回的收集器会把对流中每一个对象应用toString方法得到的所有字符串连接成一个字符串。
        // 把菜单中的所有菜肴的名称连接起来:
        String shortMenu = menu.stream().map(Dish::getName).collect(joining());
        System.out.println(shortMenu);

        // 如果Dish类有一个toString方法来返回菜肴名称，我们无需提取每一道菜名:
        // String shortMenu2 = menu.stream().collect(joining(", "));

        // joining还有一个重载版本可以接受元素之间的分界符:
        shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println(shortMenu);

        // 广义的归约汇总
        // 我们已经讨论过的所有收集器，都是reducing工厂方法的的特殊化。
        // 可以用reducing方法创建的收集器来计算你菜单的总热量:
        int totalCalories2 = menu.stream()
                .collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
        // 同样，你可以使用下面这样的reducing来找到热量最高的菜：
        Optional<Dish> mostCaloriesDish = menu.stream()
                .collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

        // 收集框架的灵活性: 以不同的方法执行同样的操作
        // 你还可以进一步简化前面使用reducing收集器的求和例子:
        totalCalories2 = menu.stream()
                .collect(reducing(0, Dish::getCalories, Integer::sum));

        // counting收集器也是类似的利用了三参数reducing工厂方法实现的。它把流中每个元素都转换为一个值为
        // 1的Long型对象，然后再把它们相加:
        //        private static <T> Collector<T, ?, Long> counting() {
        //            return reducing(0L, e -> 1L, Long::sum);
        //        }


        // 还有另一种方法不使用收集器也能执行相同操作，将菜肴流映射为每一道菜的热量：
        int totalCalories3 = menu.stream()
                .map(Dish::getCalories).reduce(Integer::sum).get();

        // 更简洁的方法是把流映射到一个IntStream，然后调用sum方法：
        int totalCalories4 = menu.stream().mapToInt(Dish::getCalories).sum();

        // 根据情况选择最佳解决方案
        // 我们的建议是，尽可能为手头的问题探索不同的解决方案，但在通用的方案里，始终选择最专门化的一个。


        /*********** 分组 ************/
        System.out.println("/*********** 分组 ************/");

        // 对菜肴按照类型分组
        Map<Type, List<Dish>> dishesByType = menu.stream()
                .collect(groupingBy(Dish::getType));

        // 如果你想把热量不到400卡路里的菜划分为低热量diet，热量400到700卡路里的菜划为普通normal，
        // 热量高于700卡路里的划为高热量fat。你可以把这个逻辑写成Lambda表达式：
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream()
                .collect(groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));

        // 现在你可以对菜肴按照类型和热量进行分组，但要想同时按照这两个标准分类怎么办？

        // 多级分组
        // 我们可以使用一个由双参数版本的Collectors.groupingBy工厂方法创建的收集器，它除了普通的分类
        // 函数外，还可以接受Collector类型的第二个参数。
        Map<Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
                menu.stream().collect(groupingBy(Dish::getType,
                        groupingBy(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        })));

        System.out.println(dishesByTypeCaloricLevel);

        // 按子组收集数据
        // 传递给第一个groupingBy的第二个收集器可以使任何类型，而不一定是另一个groupingBy。
        // 例如，要数一数菜单中每类菜有多少个，可以传递counting收集器作为groupingBy的第二个参数：
        Map<Type, Long> typesCount = menu.stream().collect(groupingBy(Dish::getType, counting()));
        System.out.println(typesCount);

        // 我们可以把前面用于查找菜单中热量最高的菜肴的收集器改一改，按照菜单类型分类：
        Map<Type, Optional<Dish>> mostCaloricByType = menu.stream()
                .collect(groupingBy(Dish::getType, maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println(mostCaloricByType);

        // 把收集器的结果转换为另一种类型 Collectors.collectingAndThen
        // 差汇总啊每个子组中热量最高的Dish
        Map<Type, Dish> mostCaloricByType2 = menu.stream()
                .collect(groupingBy(Dish::getType, collectingAndThen(
                        maxBy(Comparator.comparingInt(Dish::getCalories)),
                        Optional::get
                )));

        // 与groupingBy联合使用的其他收集器的例子
        // 通常来说，通过groupingBy工厂方法的第二个参数传递的收集器将会对分到同一组中所有流元素执行进一步归约操作。
        // 例如，对所有菜肴热量分组求和：
        Map<Type, Integer> totalCaloriesByType = menu.stream()
                .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));


        // 有时候，我们需要把groupingBy和mapping收集器结合起来
        // 比如我们想知道，对于每种类型的Dish，菜单中都有哪些CaloricLevel。我们可以把groupingBy和mapping收集器结合起来：
        Map<Type, Set<CaloricLevel>> caloricLevelsByType = menu.stream()
                .collect(groupingBy(Dish::getType, mapping(
                        dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }, toSet()
                )));
        System.out.println(caloricLevelsByType);

        // 对于toSet(), 你也可以显式的指定一个构造函数的引用来要求使用HashSet: toCollection(HashSet::new)

        /*********** 分区 ************/
        System.out.println("/*********** 分区 ************/");

        // 分区函数返回一个布尔值，因此它得到的分组Map的键类型是Boolean，于是它最多可以分为两组：true和false
        // 例如：你要把菜单按照素食和非素食分开：
        Map<Boolean, List<Dish>> partitionedMenu = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian));
        System.out.println(partitionedMenu);
        List<Dish> vegetarianDishes = partitionedMenu.get(true);

        // 分区的优势

        // partitioningBy工厂方法有一个重载版本，可以传递第二个收集器：

        // 对于分区产生的素食和非素食的子流，分别按照类型对菜肴分组
        Map<Boolean, Map<Type, List<Dish>>> vegetarianDishesByType = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        System.out.println(vegetarianDishesByType);

        // 你也可以找到素食和非素食中热量最高的菜：
        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)),
                                Optional::get)));
        System.out.println(mostCaloricPartitionedByVegetarian);

        // 将数字按质数和非质数分区
        System.out.println(partitionPrimes(100));

        /*********** 收集器接口 ************/
        System.out.println("/*********** 收集器接口 ************/");

        //        public interface Collector<T, A, R> {
        //            Supplier<A> supplier();
        //            BiConsumer<A, T> accumulator();
        //            Function<A, R> finisher();
        //            BinaryOperator<A> combiner();
        //            Set<Characteristics> characteristics();
        //        }

        // - T 是流中药收集的项目的泛型
        // - A 是累加器类型，累加器是在收集过程中用于累积部分结果的对象
        // - R 是收集操作得到的对象(通常但并不一定是集合)的类型

        // 假设我们要实现一个ToListCollector<T>类，将Stream<T>中所有的元素收集到一个List<T>里，它的签名如下:
        // public class ToListCollector<T> implements Collector<T, List<T>, List<T>>

        // 理解Collector接口声明的方法

        // 1. 建立新的结果容器: supplier方法
        // supplier方法必须返回一个结果为空的Supplier，也就是一个无参函数，在调用它时创建一个空的累加器实例，供数据收集过程使用。
        // 在我们的ToListCollector中，supplier返回一个空的List:
        //        public Supplier<List<T>> supplier() {
        //            return () -> new ArrayList<T>();
        //        }

        // 2. 将元素添加到结果容器: accumulator方法
        // accumulator方法会返回执行归约操作的函数。该函数会返回void,因为累加器是原位更新。
        // 对于ToListCollector，这个函数仅仅会把当前项目添加至已经遍历过的项目列表：
        //        public BiConsumer<List<T>, T> accumulator() {
        //            return (list, item) -> list.add(item);
        //        }

        // 3. 对结果容器应用最终转换：finisher方法
        // 在遍历完流后，finisher方法必须返回在累积过程的最后要调用的一个函数，以便将累加器对象转换为整个集合操作的最终结果。
        // 对于ToListCollector，累加器对象刚好符合预期的最终结果，因此无需转换：
        //        public Function<List<T>, List<T>> finisher() {
        //            return Function.identity();
        //        }

        // 这三个方法已经足以对流进行顺序归约。

        // 4. 合并两个结果容器：combiner方法
        // 它定义了对流的各个子部分进行并行处理，各个子部分归约所得的累加器要如何合并。
        // 对于ToList而言，这个方法非常简单，只要把从流的第二部分收集到的项目列表加到第一部分得到的列表后面就行：
        //        public BinaryOperator<List<T>> combiner() {
        //            return (list1, list2) -> {
        //                list1.addAll(list2);
        //                return list1;
        //            };
        //        }

        // 5. characteristics方法
        // 会返回一个不可变的Characteristics集合，它定义了收集器的行为，尤其是关于流是否可以并行归约，以及可以使用那些优化的提示。
        // 它是一个包含三个项目的枚举：
        // - UNORDERED 归约的结果不受流中项目的遍历和累积顺序的影响。
        // - CONCURRENT accumulator函数可以从多个线程同时调用，且该收集器可以并行归约流。
        // - IDENTITY-FINISH 这表明完成器方法返回的函数是一个恒等函数，可以跳过。这种情况下，累加器对象会直接用作归约过程的最终结果。

        // 我们的ToListCollector是IDENTITY_FINISH的，因为用来累积流中的元素的List已经是我们最终要的结果了。
        // 但它并不是UNORDERED的，因为用在有序流上的时候，我们还是希望顺序能够保留在得到的List中。
        // 它是CONCURRENT的，但仅仅在背后的数据源无序时才会并行处理。

        // 全部融合到一起
        /**
         * {@link ToListCollector}
         */

        /*********** 开发你自己的收集器 ************/
        System.out.println("/*********** 开发你自己的收集器 ************/");

        /**
         * {@link PrimeNumbersCollector}
         */
        System.out.println(partitionPrimesWithCustomCollector(100));

        // 比较收集器性能
        /**
         * {@link CollectorHarness}
         */

        // 也可以通过把实现PrimeNumbersCollector核心逻辑的三个函数传给collect方法的重载版本来获取同样的结果:
        System.out.println(partitionPrimesWithCustomCollectorBeta(100));
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(new PrimeNumbersCollector());
    }

    public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

        @Override
        public Supplier<List<T>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<T>, T> accumulator() {
            return List::add;
        }

        @Override
        public BinaryOperator<List<T>> combiner() {
            return (list1, list2) -> {
                list1.addAll(list2);
                return list1;
            };

        }

        @Override
        public Function<List<T>, List<T>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
        }
    }

    public enum CaloricLevel {
        DIET, NORMAL, FAT
    }

    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(partitioningBy(CollectorsTest::isPrime));
    }

    /**
     * 判断candidate是否为质数
     * @param candidate candidate
     * @return boolean
     */
    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }

    /**
     * 仅用质数做除数，而且仅仅用小于被测数平方根的质数来测试
     * @param primes
     * @param candidate
     * @return
     */
    public static boolean isPrime(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return takeWhile(primes, i -> i <= candidateRoot)
                .stream()
                .noneMatch(p -> candidate % p == 0);
    }

    /**
     * 给定一个排序列表和一个谓词，它会返回元素满足谓词的最长前缀
     * @param list
     * @param p
     * @param <A>
     * @return
     */
    public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
        int i = 0;
        for (A item : list) {
            if (!p.test(item)) {
                return list.subList(0, i);
            }
            i++;
        }

        return list;
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollectorBeta(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(
                        // 供应源
                        () -> new HashMap<Boolean, List<Integer>>() {{
                            put(true, new ArrayList<>());
                            put(false, new ArrayList<>());
                        }},

                        // 累加器
                        (acc, candidate) -> {
                            acc.get(isPrime(acc.get(true), candidate))
                                    .add(candidate);
                        },

                        // 组合器
                        (map1, map2) -> {
                            map1.get(true).addAll(map2.get(true));
                            map1.get(false).addAll(map2.get(false));
                        }
                );
    }
}




























