package online.wangxuan.java8.chap11;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * @author wangxuan
 * @date 2019/1/17 11:47 PM
 */

public class CompletableFutureDemo {

    public static void main(String[] args) {

        /**
         * 本章内容：
         *
         * - 创建异步计算，并获取计算结果
         * - 使用非阻塞操作提升吞吐量
         * - 设计和实现异步API
         * - 如何以异步的方式使用同步的API
         * - 如何对两个或多个异步操作进行流水线和合并操作
         * - 如何处理异步操作的完成状态
         */

        // 并发和并行

        //           并发
        // 处理器核1        处理器核2
        //    |
        //  任务1              |
        //    |
        //  任务2              |
        //    |
        //  任务1              |

        //           并行
        // 处理器核1        处理器核2
        //    |               |
        //    |               |
        //    |               |
        //  任务1            任务2
        //    |               |
        //    |               |

        /************** 11.1 Future接口 **************/
        System.out.println("/************** 11.1 Future接口 **************/");
        System.out.println();

        // Future接口的设计初衷是对将来某个时刻会发生的结果进行建模。
        // 打个比方，你可以把它想象成这样的场景：你拿了一袋子衣服到你中意的干洗店去洗。干洗店的员工会给你一张发票，
        // 告诉你什么时候你的衣服会洗好(这就是一个Future事件)。衣服干洗的同事，你可以去做其他的事情。

        // Future的另一个优点是它比更底层的Thread更易用。要使用Future，通常你只需要将耗时的操作封装在一个Callable
        // 对象中，再将它提交给ExecutorService，就万事大吉了。

        // 下面代码展示了Java 8之前使用Future的一个例子：
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Double> future = executor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return doSomeLongComputation();
            }
        });

        doSomethingElse();

        try {
            Double result = future.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException ee) {
            // 计算抛出一个异常
        } catch (InterruptedException ie) {
            // 当前线程在等待过程中被中断
        } catch (TimeoutException te) {
            // 在Future对象完成之前超时
        }

        // 1. Future接口的局限性

        // - 将两个异步计算合并为一个，这两个异步计算之间相互独立，同时第二个又依赖于第一个的结果。
        // - 等待Future集合中的所有任务都完成。
        // - 仅等待Future集合中最快结束的任务完成，(有可能因为它们试图通过不同的方式计算同一个值)，并返回它的结果。
        // - 通过编程方式完成一个Future任务的执行 (即以手工设定异步操作结果的方式)。
        // - 应对Future的完成事件(即当Futur的完成事件发生时会收到通知，并能使用Future计算的结果进行下一步操作，不只是简单地阻塞等待操作的结果)。

        // CompletableFuture类利用java8的特性以更直观的方式将上述需求都变为可能。
        // CompletableFuture和Future的关系就跟Stream和Collection的关系一样。

        // 2. 使用CompletableFuture构建异步应用

        // 为了展示CompletableFuture的强大特性，我们会创建一个名为「最佳价格查询器」的应用。
        // 它会查询多个在线商店，依据给定的产品或服务找出最低的价格。

        // 你会学到几个重要技能：
        // - 首先，你会学到如何为你的客户提供异步API
        // - 其次，你会掌握如何让你使用同步API的代码变为非阻塞代码。
        // - 你还会学到如何以响应式的方式处理一步操作的完成事件。

        /************** 11.2 实现异步API **************/
        System.out.println("/************** 11.2 实现异步API **************/");
        System.out.println();

        /**
         * {@link Shop}
         */

        /************** 11.3 让你的代码免受阻塞之苦 **************/
        System.out.println("/************** 11.3 让你的代码免受阻塞之苦 **************/");
        System.out.println();

        System.out.println("处理器的核的数目: " + Runtime.getRuntime().availableProcessors());
        System.out.println("CompletableFuture和并行流默认线程池的线程数: " + Runtime.getRuntime().availableProcessors());

        System.out.println("采用顺序查询所有商店的方式实现的findPrices方法: ");
        testMethod(Shop::findPrices);

        // 1. 使用并行流对请求进行并行操作
        System.out.println("使用并行流对请求进行并行操作: ");
        testMethod(Shop::findPrices2);

        // 2. 使用CompletableFuture实现findPrices方法
        System.out.println("使用CompletableFuture实现findPrices方法: ");
        testMethod(Shop::findPrices3);

        // 3. 寻找更好的方案

        /**
         * 如果你试图让你的代码处理9个商店，并行流版本耗时3143毫秒，而CompletableFuture版本耗时3009毫秒。
         * 它们看起来不相伯仲，究其原因都一样，它们内部采用的是同样的通用线程池，默认都使用固定数目的线程，
         * 具体线程数取决于Runtime.getRuntime().availableProcessors()的返回值。
         *
         * 然而，CompletableFuture具有一定的优势，因为它允许你对执行器(Executor)进行配置，尤其是线程池
         * 的大小，让它以更适合应用需求的方式进行配置，满足程序的要求，而这时并行流API无法提供的。
         */

        // 4. 使用定制的执行器

        /**
         * 调整线程池大小
         *
         * Brain Goetz建议，线程池大小与处理器的利用率之比可以使用下面的公式进行估算：
         *
         * N threads = N cpu * U cpu * (1 + W/C)
         *
         * 其中：
         * N cpu 是处理器的核心数目，可以通过Runtime.getRuntime().availableProcessors()得到
         * U cpu 是期望的CPU的利用率 (该值介于0和1之间)
         * W/C 是等待时间与计算时间的比率
         */

        // 实际操作中，我们建议你将执行器使用的线程数，与你需要查询的商店数目设定为同一个值，这样每个商店都
        // 应该对应一个服务线程。不过，为了避免发生由于商店的数目过多导致服务器超负荷，你还是需要设置一个上限。
        System.out.println("使用定制的执行器实现findPrices方法: ");
        testMethod(Shop::findPrices4);

        // 改进之后，使用CompletableFuture方案的程序处理9个商店耗时1022秒。一般而言，这种状态会一直持续，
        // 直到商店的数目达到我们之前计算的阈值400。
        // 这个例子证明了，要创建更适合你的应用特性的执行器，利用CompletableFutures向其提交任务执行是个不错
        // 的主意。处理需大量使用的异步操作的情况时，这几乎是最有效的策略。

        /**
         * 并行：使用流还是CompletableFutures？
         *
         * - 如果你进行的是计算密集型操作，并且没有I/O，那么推荐使用Stream接口，因为实现简单，同时效率是最高的。
         *   (如果所有的线程都是计算密集型的，那就没必要创建比处理器核数更多的线程)
         *
         * - 反之，如果你并行国内工作单元还涉及等待I/O的操作(包括网络连接等待)，那么使用CompletableFuture灵活性
         *   更好，你可以像前文讨论的那样，根据等待/计算，或者W/C的比率设定需要使用的线程数。
         */

        /************** 11.4 对多个异步任务进行流水线操作 **************/
        System.out.println("/************** 11.4 对多个异步任务进行流水线操作 **************/");
        System.out.println();

        /**
         * 假设所有商店都统一使用一个集中式的折扣服务。提供了五个不同的折扣代码，每个折扣代码对应不同的折扣率。
         * {@link Discount}
         */

        // 新的getPrice方法
        Shop shop = new Shop("BuyItAll");
        System.out.println(shop.getPrice2("myPhone27S"));

        // 1. 实现折扣服务

        // 我们将 对商店返回字符串的解析操作封装到了下面的Quote类中：
        /**
         * 通过传递shop对象返回的字符串给静态工厂方法parse，你可以得到一个Quote实例。
         * {@link Quote}
         */

        // Discount服务还提供一个applyDiscount方法，接收一个Quote对象，返回一个字符串

        // 2. 使用Discount服务

        // 首先尝试以最直接的方式(这种方式是顺序而且同步执行的)，重新实现findPrices，以满足这些新增需求：
        System.out.println("最直接的方式(这种方式是顺序而且同步执行的)实现findPrices: ");
        testMethod(Shop::findPrices5);

        // 这次执行耗时8秒，把流转换为并行流时，非常容易提升该程序的性能。
        // 但是，这种方案在商店数目增加时，扩展性不好，因为Stream底层依赖的是固定数目的通用线程池。
        // 相反，如果自定义CompletableFuture调度任务执行的执行器能够更充分的利用CPU资源。

        // 3. 构造同步和异步操作
        System.out.println("使用CompletableFuture实现findPrices: ");
        testMethod(Shop::findPrices6);

        /**
         * - 从shop对象中获取价格，接着把价格转换为Quote。
         * - 拿到返回的Quote对象，将其作为参数传递给Discount服务，取得最终的折扣价格。
         *
         * Java 8 的CompletableFuture API提供了名为thenCompose的方法，它允许你对两个异步操作进行流水线，
         * 第一个操作完成时，将其作为参数传递给第二个操作。
         *
         * 换句话说，你可以创建两个CompletableFutures对象，对第一个CompletableFuture对象调用thenCompose，
         * 并向其传递一个函数。当第一个CompletableFuture执行完毕后，它的结果将作为该函数的参数。
         *
         * CompletableFuture类也提供了一个Async后缀结尾的thenComposeAsync。
         * 通常而言，名称中不带Async的方法和它的前一个任务一样，在同一个线程中运行，而以Async结尾的方法会将后续的任务提交到一个线程池。
         * 就这个例子而言，第二个CompletableFuture对象的结果依赖于前一个CompletableFuture，所以选择thenCompose，减少了线程切换开销。
         */

        // 4. 将两个CompletableFuture对象整合起来，无论它们是否存在依赖

        /**
         * 一种比较常见的情况是，你需要将两个完全不相干的CompletableFuture对象的结果整合起来，
         * 而且你不希望等到第一个任务完全结束才开始第二项任务。
         *
         * 这时，你应该使用thenCombine方法，它接收名为BiFunction的第二参数，这个参数定义了当两个CompletableFuture
         * 对象完成计算后，结果如何合并。
         *
         * thenCombine方法也提供一个Async的版本。这里，如果使用thenCombineAsync会导致BiFunction中定义的合并
         * 操作被提交到线程池中，由另一个任务以异步的方式执行。
         */

        // 例子，有一家商店提供的价格是以欧元计价的，但是你希望以美元的方式提供给客户。
        // 你可以使用异步的方式向商店查询指定商品的价格，同时从远程的汇率服务哪里查到欧元和美元之间的汇率。
        // 当二者都结束时，再将这两个结果结合起来。

        Future<Double> futurePriceInUSD =
                // 创建第一个任务查询商店取得商品的价格
        CompletableFuture.supplyAsync(() -> shop.getPrice("myPhone27S"))
                .thenCombine(
                        CompletableFuture.supplyAsync(
                                // 创建第二个独立任务，查询美元和欧元之间的转换汇率
                                () -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)),
                        // 通过乘法整合得到商品价格和汇率
                        (price, rate) -> price * rate
                );

        // 5. 对Future和CompletableFuture的回顾

        // 为了更直观的感受一下使用CompletableFuture在代码可读性上带来的巨大提升，尝试用Java 7实现上述功能：
        ExecutorService executors = Executors.newCachedThreadPool();
        final Future<Double> futureRate = executors.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD);
            }
        });
        Future<Double> futurePriceInUSD2 = executors.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                double priceInEUR = shop.getPrice("myPhone27S");
                return priceInEUR * futureRate.get();
            }
        });

        /************** 11.5 响应CompletableFuture的completion事件 **************/
        System.out.println("/************** 11.5 响应CompletableFuture的completion事件 **************/");
        System.out.println();

        // 1. 对最佳价格查询器应用的优化
        Shop.findPrice7("myPhone27S");

        // 2. 付诸实践


        System.exit(0);
    }


    private static void doSomethingElse() {

    }

    private static Double doSomeLongComputation() {
        return null;
    }

    private static void testMethod(Function<String, List<String>> findPrice) {
        long start = System.nanoTime();
        System.out.println(findPrice.apply("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + "msecs");
    }

}
