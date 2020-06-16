package online.wangxuan.java8.chap7;

import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author wangxuan
 * @date 2018/11/18 1:36 PM
 */

public class ParallelTest {

    public static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    public static void main(String[] args) {

        /************** 7.1 并行流 **************/
        System.out.println("/************** 7.1 并行流 **************/");
        System.out.println();

        // 可以通过对收集源调用parallelStream方法来把集合转换为并行流。
        // 并行流就是一个把内容分成多个数据块，并用不同的线程处理每个数据块的流。

        // 假设你需要写一个方法，接受数字n作为参数，并返回从1到给定参数的所有数字和。
        // 一个直接的方法就是生成一个无穷大的数字流，把它限制到给定的数目，然后用对两个数字求和的BinaryOperator来归约这个流：
        sequentialSum(100);
        // 上面的方法与下面的迭代等价：
        iterativeSum(100);

        // 将顺序流转换为并行流
        parallelSum(100);

        // 实际上，对顺序流调用parallel方法并不意味着流本身有任何实际的变化。它在内部实际上就是设了一个boolean标志，
        // 表示你想让调用parallel之后进行的所有操作都并行执行。

        // 类似的，你只需要对并行流调用sequential方法，就可以把它变成顺序流。你可以把这两个方法结合起来，就可以更细化的
        // 控制在遍历流时哪些操作要并行执行，哪些要顺序执行。比如：
        //        stream.parallel()
        //                .filter(...)
        //                .sequential()
        //                .map(...)
        //                .parallel()
        //                .reduce();

        // 最后一次parallel或sequential调用会影响整个流水线。

        // 看看流的parallel方法，你可能会想，并行流用的线程是从哪儿来的？有多少个？怎么自定义这个过程？
        // 并行流内部使用了默认的ForkJoinPool，它默认的线程数量就是你的处理器数量。

        // 测量流性能
        // Sequential sum done in: 84 ms
        System.out.println("Sequential sum done in: " +
                measureSumPref(ParallelTest::sequentialSum, 1_000_000) + " ms");
        // 用传统for循环的迭代版本执行起来会快很多，因为它更底层，更重要的是不需要对原始类型做任何装箱或拆箱操作：
        // Iterative sum done in: 2 ms
        System.out.println("Iterative sum done in: " +
                measureSumPref(ParallelTest::iterativeSum, 1_000_000) + " ms");
        // 并行版本：
        // Parallel sum done in: 95 ms
        System.out.println("Parallel sum done in: " +
                measureSumPref(ParallelTest::parallelSum, 1_000_000) + " ms");

        // 结果令人失望，求和方法的并行版本比顺序版本要慢很多。原因是这里有两个问题：
        // - iterate生成的是装箱的对象，必须拆箱成数字才能求和
        // - 我们很难把iterate分成多个独立块来并行执行

        // 对于iterate，整张数字列表在归纳过程开始时都没有准备好，因而无法有效的把流划分成小块并行处理
        // 把流标记成并行，你其实是给顺序处理增加了开销。它还要把每次求和操作分到一个不同的线程上。

        // 这说明了并行编程可能很复杂，有时候甚至有点违反直觉。如果用的不对，它甚至可能让程序的整体性能更差。
        // 所以在调用那个看似神奇的parallel操作时，了解背后到底发生了什么是很有必要的。

        // 使用更有针对性的方法
        // 使用LongStream.rangeClosed方法：
        // - 这个方法会直接产生原始类型的long数字，没有装箱拆箱的开销
        // - 会生成数字范围，很容易拆分成独立的小块。
        // Ranged sum done in: 5 ms
        System.out.println("Ranged sum done in: " +
                measureSumPref(ParallelTest::rangedSum, 1_000_000) + " ms");

        // 避免了自动拆装箱，速度快了很多。
        // 并行 ParallelRanged sum done in: 1 ms
        System.out.println("ParallelRanged sum done in: " +
                measureSumPref(ParallelTest::parallelRangedSum, 1_000_000) + " ms");

        // 并行并不是没有代价的，并行化过程本身需要对流做递归划分，把每个子流的归纳操作分配到不同的线程，然后把
        // 这些操作的结果合并成一个值。但在多个内核之间移动数据的代价也可能比你想象的大，所以很重要的一点是要保证
        // 在内核中并行执行工作的时间比在内核之间传输数据的时间长。

        // 正确的使用并行流
        // 错用并行流的首要原因就是使用的算法改变了某些共享状态。下面是另一种实现对前n个自然数求和的方法，但这会
        // 改变一个共享累加器：
        System.out.println("sideEffect sum done in: " +
                measureSumPref(ParallelTest::sideEffectSum, 1_000_000) + " ms");

        // 让我们试着把Stream变成并行的:
        System.out.println("sideEffectParallel sum done in: " +
                measureSumPref(ParallelTest::sideEffectParallelSum, 1_000_000) + " ms");
        // 因为它本质上就是顺序的，每次访问total都会出现数据竞争。所以结果就会出现错误。


        // 高效的使用并行流
        // 一些帮助你决定是否使用并行流的意见：
        // 1. 如果有疑问，测试。把顺序流转成并行流轻而易举。但并行流并不总是比顺序流快。所以考虑选择并行流时，要检查其性能。
        // 2. 留意装箱。自动装箱和拆箱操作会大大降低性能。Java8中有原始类型流来避免这种操作，但凡可能都应该用这些流。
        // 3. 有些操作本身在并行流上的性能就比顺序流差。特别是limit和findFirst这些依赖于元素顺序的操作。它们在并行流上执行的代价非常大。你总是可以调用unordered方法来把有序流变成无序流。
        // 4. 还要考虑流的总计算成本。一个元素通过流水线的处理成本越高，使用流时性能好的可能性就越大。
        // 5. 对于较小的数据量，使用并行流从来都不是一个好的决定。
        // 6. 要考虑流背后的数据结构是否易于分解。例如ArrayList的拆分效率比LinkedList高得多，因为前者用不着遍历就可以平均拆分。
        // 7. 要考虑终端操作中合并步骤的代价是大是小。如果这一步代价很大，那么组合每个子流产生的部分结果所付出的代价就可能超出通过并行流得到的性能提升。



        // 并行流背后使用的基础架构是Java7中引入的分支/合并框架。

        /************** 7.2 分支/合并框架 **************/
        System.out.println("/************** 7.2 分支/合并框架 **************/");
        System.out.println();

        // 分支/合并框架的目的是以递归的方式将可以并行的任务拆分成更小的任务，然后将每个子任务的结果合并起来生成整体结果。
        // 它是ExecutorService接口的一个实现，它把子任务分配给线程池中的工作线程。

        // 使用RecursiveTask

        // 要把任务提交到这个池，必须创建RecursiveTask<R>的一个子类，其中R是并行化任务产生的结果类型。
        // 要定义RecursiveTask，只需要实现它唯一的抽象方法compute:
        // protected abstract R compute();
        // 这个方法同时定义了将任务拆分成子任务的逻辑，以及无法再拆分或不方便再拆分时，生成单个子任务结果的逻辑。

        // 用分支/合并框架执行并行求和：
        /**
         * {@link ForkJoinSumCalculator}
         */
        System.out.println("ForkJoin sum done in: " +
                measureSumPref(ParallelTest::forkJoinSum, 1_000_000) + " ms");

        // 使用分支/合并框架的最佳做法

        // - 对一个任务调用join方法会阻塞调用方，直到该任务做出结果。因此，有必要在两个子任务的计算都开始之后再调用它。否则你得到的版本比原始的顺序算法更慢更复杂，因为每个子任务都必须等待另一个子任务完成才能启动。
        // - 不应该在RecursiveTask内部使用ForkJoinPool的invoke方法。
        // todo ...

        // 工作窃取

        // 在理想的情况下，划分并行任务时，应该让每个任务都用完全相同的时间完成，让所有CPU内核都同样繁忙。不幸的是，实际上
        // 每个子任务花费的时间可能天差地别，要么是因为划分策略效率低，要么是有不可预知的原因。

        // 分支/合并框架工程用一种称为工作窃取的技术来解决这个问题。在实际应用中，这意味着这些任务差不多被平均分配到ForkJoinPool
        // 中的所有线程上。每个线程都为分配给它的任务保存一个双向链式队列，每完成一个任务，就会从队列头上取出下一个任务开始执行。
        // 基于前面所述的原因，某个线程可能早早完成了分配给它的所有任务，也就是它的队列已经空了，而其他的线程还很忙。这时，这个线程
        // 并没有闲下来，而是随机选了一个别的线程，从队列的尾巴上 `偷走` 一个任务。这个过程一直继续下去，直到所有任务都执行完毕。

        /************** 7.3 Spliterator 可分迭代器 **************/
        System.out.println("/************** 7.3 Spliterator 可分迭代器 **************/");
        System.out.println();

        // 和Iterator一样，Spliterator也用于遍历数据源中的元素，但它是为了并行执行而设计的。
        // Java8已经为集合框架中包含的所有数据结构提供了一个默认的Spliterator实现。

        // Spliterator接口：
        //        public interface Spliterator<T> {
        //            boolean tryAdvance(Consumer<? super T> action);
        //            Spliterator<T> trySplit();
        //            long estimateSize();
        //            int characteristics();
        //        }

        // 其中：
        // tryAdvance方法的行为类似于普通的Iterator，因为它会按顺序一个一个使用Spliterator中的元素，并且如果还有其他元素要遍历就返回true。
        // trySplit是专为Spliterator接口设计的，因为它可以把一些元素划出去分给第二个Spliterator，它它们两个并行处理。
        // estimateSize方法估计还剩下多少元素要遍历。

        // 拆分过程

        // 将Stream拆分成多个部分的算法是一个递归的过程。这个拆分过程受Spliterator本身特性影响。而特性是通过characteristics方法声明的。
        // Spliterator接口声明的最后一个抽象方法是characteristics，它将返回一个int，代表Spliterator本身特性集的编码。使用Spliterator
        // 的客户可以用这些特性来更好的控制和优化它的使用。

        // 实现你自己的Spliterator

        // 我们要开发一个简单地方法来数数一个String中的单词数。

        // 一个迭代式字数统计方法：
        System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");

        // 以函数式风格重写单词计数器
        /**
         * {@link WordCounter}
         */
        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
                .mapToObj(SENTENCE::charAt);
        System.out.println("Found " + countWords(stream) + " words");

        // 让WordCounter并行工作
        /**
         * {@link WordCounterSpliterator}
         */
        Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> stream1 = StreamSupport.stream(spliterator, true);
        System.out.println("Found " + countWords(stream1) + " words");
    }

    final static String SENTENCE = " Nel   mezzo del cammin  di nostra  vita " +
            "mi  ritrovai in una  selva oscura" +
            " che la  dritta via era   smarrita ";

    private static int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumulate, WordCounter::combine);
        return wordCounter.getCounter();
    }

    public static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                // 上一个字符是空格，而当前遍历的字符不是空格时，将单词计数器加一
                if (lastSpace) counter++;
                lastSpace = false;
            }
        }
        return counter;
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return FORK_JOIN_POOL.invoke(task);
    }

    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel()
                .forEach(accumulator::add);

        return accumulator.total;
    }

    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    public static class Accumulator {
        public long total = 0;
        public void add(long value) {
            total += value;
        }
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0, Long::sum);
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public static long measureSumPref(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest) fastest = duration;
        }

        return fastest;
    }
}
