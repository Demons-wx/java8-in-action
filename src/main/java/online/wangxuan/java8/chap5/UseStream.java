package online.wangxuan.java8.chap5;


import online.wangxuan.java8.chap4.Dish;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static online.wangxuan.java8.chap4.Dish.*;

/**
 * @author wangxuan
 * @date 2018/11/10 7:08 PM
 */

public class UseStream {

    public static void main(String[] args) {

        /********** 5.1 筛选和切片 **********/
        System.out.println("/********** 5.1 筛选和切片 **********/");

        // 筛选出所有素菜
        List<Dish> vegetarianMenu = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList());

        // 筛选出各异的元素
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        // 截短流 选出热量超过300卡路里的头三道菜
        List<Dish> dishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .collect(Collectors.toList());

        // 跳过元素
        // 流还支持skip(n)方法, 返回一个扔掉前n个元素的流. 如果流中的元素不足n个, 则返回一个空流
        List<Dish> dishes2 = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(Collectors.toList());

        /********** 5.2 映射 **********/
        System.out.println("/********** 5.2 映射 **********/");

        // 对流中每一个元素应用函数
        // 流支持map方法, 它接受一个函数作为参数, 这个函数会被应用到每个元素上, 并将其映射成一个新的元素
        List<String> dishNames = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.toList());

        // 给你一个单词列表, 返回另一个列表, 显示每个单词中有几个字母
        List<String> words = Arrays.asList("Java 8", "Lambdas", "In", "Action");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(Collectors.toList());

        // 流的扁平化
        // 给定一张单词表, 返回一张列表, 列出里面各不相同的字符?
        List<String> words2 = Arrays.asList("Hello", "World");

        // 这个方法问题在于, 传递给map方法的Lambda为每个单词返回了一个String[]。
        // 因此, map返回的流实际上是Stream<String[]>类型的。
        List<String[]> s1 = words2.stream()
                .map(word -> word.split(""))
                .distinct()
                .collect(Collectors.toList());

        // 尝试使用map和Arrays.stream()
        // Arrays.stream()可以接受一个数组并产生一个流
        List<Stream<String>> s2 = words2.stream()
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        // 使用flatMap
        // 使用flatMap的效果是, 各个数组并不是分别映射成一个流, 而是映射成流的内容。
        // 所有使用map(Arrays::stream)时生成的单个流都被合并起来, 即扁平化为一个流
        List<String> uniqueCharacters = words2.stream()
                .map(w -> w.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        // 给定两个数字列表，如何返回所有的数对呢？
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))
                .collect(Collectors.toList());
        pairs.forEach(arr -> {
            System.out.println(arr[0] + "," + arr[1]);
        });

        System.out.println("------------");

        // 只返回总和能被3整除的数对呢?
        List<int[]> pairs2 = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                .filter(j -> (i + j) % 3 == 0)
                .map(j -> new int[]{i ,j}))
                .collect(Collectors.toList());
        pairs2.forEach(arr -> {
            System.out.println(arr[0] + "," + arr[1]);
        });

        /********** 5.3 查找和匹配 **********/
        System.out.println("/********** 5.3 查找和匹配 **********/");

        // 检查谓词是否至少匹配一个元素
        // anyMatch方法可以回答: 流中是否有一个元素能匹配给定的谓词
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }

        // 检查谓词是否匹配所有元素
        boolean isHealthy = menu.stream()
                .allMatch(d -> d.getCalories() < 1000);

        // noneMatch
        isHealthy = menu.stream()
                .noneMatch(d -> d.getCalories() >= 1000);

        // 查找元素
        // 找到一道素食菜肴
        Optional<Dish> dish = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();

        // Optional
        // 是一个容器类，代表一个值存在或不存在。
        // Optional里面有几种可以迫使你显式的检查值是否存在或处理值不存在的情形：
        // - isPresent() 将在Optional包含值的时候返回true，否则返回false
        // - ifPresent(Consumer<T> block) 会在值存在的时候执行给定的代码块
        // - T get() 会在值存在时返回值，否则抛出一个NoSuchElement异常
        // - T orElse(T other) 会在值存在时返回值，否则返回一个默认值

        // 例如，你需要前面的代码是否存在一道菜并访问其名称：
        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(d -> System.out.println(d.getName()));

        // 查找第一个元素
        // 给定一个数字列表，找出第一个平方能被3整除的数
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree = someNumbers.stream()
                .map(x -> x * x)
                .filter(x -> x % 3 == 0)
                .findFirst();

        /********** 5.4 归约 **********/
        System.out.println("/********** 5.4 归约 **********/");

        // 元素求和
        // 对流中的所有元素求和
        List<Integer> numbers3 = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers3.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        // reduce接收两个参数:
        // 一个初始值，这里是0
        // 一个BinaryOperator<T> 将两个元素结合起来产生一个新值

        // 把所有元素相乘
        int product = numbers3.stream().reduce(1, (a, b) -> a * b);
        System.out.println(product);

        // 使用方法引用
        sum = numbers3.stream().reduce(0, Integer::sum);

        // 无初始值
        Optional<Integer> sum2 = numbers3.stream().reduce((a, b) -> (a + b));

        // 最大值和最小值
        Optional<Integer> max = numbers3.stream().reduce(Integer::max);
        Optional<Integer> min = numbers3.stream().reduce(Integer::min);


        /********** 5.6 数值流 **********/
        System.out.println("/********** 5.6 数值流 **********/");

        // 原始类型流特化
        // 原始类型的流特化的原因并不在于流的复杂性，而是装箱造成的复杂性。即int和Integer之间的效率差异

         // 映射到数值流
        // 这里mapToInt会返回一个IntStream而不是Stream<Integer>
        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();

        // 转换回对象流
        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> stream = intStream.boxed();

        // 默认值OptionalInt
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

        // 如果没有最大值的话，你就可以显式处理OptionalInt去定义一个默认值:
        int max2 = maxCalories.orElse(1);

        // 数值范围
        // Java8引入了IntStream和LongStream的静态方法，帮助生成数值范围。
        // - range 不包含结束值
        // - rangeClosed 包含结束值
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count());

        // 数值流应用: 勾股数
        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a ->
                IntStream.rangeClosed(a, 100)
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                .mapToObj(b -> new int[]{a, b, (int)Math.sqrt(a * a + b * b)}));

        pythagoreanTriples.limit(5)
                .forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        // better
        Stream<double[]> pythagoreanTriples2 = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a ->
                IntStream.rangeClosed(a, 100)
                .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                .filter(t -> t[2] % 1 == 0));


        /********** 5.7 构建流 **********/
        System.out.println("/********** 5.7 构建流 **********/");

        // 由值创建流
        // 你可以使用静态方法Stream.of，通过显式值创建一个流
        Stream<String> stream2 = Stream.of("Java 8", "Lambda", "In", "Action");
        stream2.map(String::toUpperCase).forEach(System.out::println);

        // 你也可以使用empty得到一个空流：
        Stream<String> emptyStream = Stream.empty();

        // 由数组创建流
        int[] numbers4 = {2, 3, 5, 7, 11, 13};
        int sum3 = Arrays.stream(numbers4).sum();

        // 由文件生成流
        // java.nio.file.Files中很多静态方法都会返回一个流，例如Files.lines，它会返回一个由指定
        // 文件中的各行构成的字符串流。
        long uniqueWords = 0;
        try (Stream<String> lines =
                     Files.lines(Paths.get("/Users/wangxuan/IdeaProjects/java8-in-action/src/" +
                             "main/resources/online/wangxuan/chap3/data.txt"), Charset.defaultCharset())){
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct().count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 由函数生成流：创建无限流
        // Stream.iterate和Stream.generate。这两个操作可以创建所谓的无限流。
        // 不像从固定集合创建的流那样有固定大小的流。
        // 由iterate和generate产生的流会用给定的函数按需创建值,
        // 一般来说，应该使用limit(n)来对这种流加以限制。

        // 迭代
        // iterator接收一个初始值，还有一个依次应用在每个产生的新值上的Lambda。
        // 这里我们使用n -> n + 2，返回前一个元素加上2。
        // 因此，iterator方法生成了一个所有正偶数的流。
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);

        // 斐波那契元组序列
        Stream.iterate(new int[]{0, 1}, arr -> new int[]{arr[1], arr[0] + arr[1]})
                .limit(20)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));

        // 生成
        // generate方法也可以让你按需生成一个无限流。它接受一个Supplier<T>类型的Lambda提供的新值
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

        // 对于斐波那契任务，你需要建立一个IntSupplier，它要把前一项的值保存在状态中，
        // 以便getAsInt用来计算下一项。此外，在下一次调用它的时候，还要更新IntSupplier的状态。
        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;
            @Override
            public int getAsInt() {
                int oldPrevious = this.previous;
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return oldPrevious;
            }
        };
        IntStream.generate(fib).limit(10).forEach(System.out::println);

        // 需要注意，getAsInt在调用时会改变对象的状态，由此在每次调用的时产生新的值。
        // 相比之下，使用iterator是纯粹不变的，它没有修改现有的状态，但在每次迭代时会创建新的元组。
        // 你应该始终采用不变的方法，以便并行处理流，并保持结果正确。


        /**
         * filter和map等操作是无状态的，它们并不存储任何状态。reduce等操作要存储状态才
         * 能计算出一个值。sorted和distinct等操作也要存储状态，因为它们需要把流中的所
         * 有元素缓存起来才能返回一个新的流。这种操作称为有状态操作。
         */
    }
}







