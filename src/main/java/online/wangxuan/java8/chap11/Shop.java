package online.wangxuan.java8.chap11;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static online.wangxuan.java8.chap11.Util.*;
import static java.util.stream.Collectors.*;

/**
 * @author wangxuan
 * @date 2019/1/20 10:20 PM
 */

public class Shop {

    private String name;

    public Shop(String name) {
        this.name = name;
    }

    private static List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("wangxuan"));

    // 为 最优价格查询器 应用定制的执行器
    private final static Executor executor =
            // 创建一个线程池，线程池中线程的数目为100和商店数目二者中较小的一个值
            Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(r);
                            // 使用守护线程，这种方式不会阻止程序的关停
                            t.setDaemon(true);
                            return t;
                        }
                    });


    private static Random random =  new Random();

    /**
     * 首先商店应该声明依据指定产品名称返回价格的方法：
     * @param product
     * @return
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /**
     *
     * @param product
     * @return
     */
    public Future<Double> getPriceAsync(String product) {
        // 创建CompletableFuture对象，它会包含计算的结果
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                // 在另一个线程中以异步方式执行计算
                double price = calculatePrice(product);
                // 需长时间计算的任务结束并得出结果时，设置Future的返回值
                futurePrice.complete(price);
            } catch (Exception e) {
                // 抛出CompletableFuture内的异常
                futurePrice.completeExceptionally(e);
            }
        }).start();

        // 无需等待还没结束的计算，直接返回Future对象
        return futurePrice;
    }

    /**
     * supplyAsync方法接受一个生产者(Supplier)作为参数，返回一个CompletableFuture对象，
     * 该对象完成异步执行后会读取调用生产者方法的返回值。
     *
     * 生产者方法会交由ForkJoinPool池中的某个执行线程(Executor)运行，但是你也可以使用SupplyAsync
     * 的重载版本，传递第二个参数指定不同的执行线程执行生产者方法。
     * @param product
     * @return
     */
    public Future<Double> getPriceAsync2(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    /**
     * 下面的方法，接受产品名作为参数，返回一个字符串列表，这个字符串列表中包括商店的名称、
     * 该商店中指定商品的价格：
     *
     * 采用顺序查询所有商店的方式实现的findPrices方法
     * @param product
     * @return
     */
    public static List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    /**
     * 对findPrices进行并行操作
     * @param product
     * @return
     */
    public static List<String> findPrices2(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    /**
     * 使用CompletableFuture实现findPrices方法
     * @param product
     * @return
     */
    public static List<String> findPrices3(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        // 使用CompletableFuture以异步方式极端每种商品的价格
                    .map(shop -> CompletableFuture.supplyAsync(
                            () -> shop.getName() + " price is " + shop.getPrice(product)
                    )).collect(Collectors.toList());

        // 等待所有异步操作结束
        return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    /**
     * 使用定制的执行器实现findPrices方法
     * @param product
     * @return
     */
    public static List<String> findPrices4(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getName() + " price is " + shop.getPrice(product), executor
                )).collect(Collectors.toList());
        return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    private static double calculatePrice(String product) {
//        delay();
        randomDelay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * getPrice现在以ShopName:price:DiscountCode的格式返回一个String类型的值。
     * @param product
     * @return
     */
    public String getPrice2(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    /**
     * 以最简单的方式实现使用Discount服务的findPrices方法
     * @param product
     * @return
     */
    public static List<String> findPrices5(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice2(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    public static List<String> findPrices6(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                // 以异步方式取得每个shop中指定产品的原始价格
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice2(product), executor))
                // Quote对象存在时，对其返回的值进行转换
                .map(future -> future.thenApply(Quote::parse))
                // 使用另一个异步任务构造期望的Future，申请折扣
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(
                        () -> Discount.applyDiscount(quote), executor
                ))).collect(toList());

        // 等待流中所有Future执行完毕，并提取各自的返回值
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    public static void findPrice7(String product) {
        long start = System.nanoTime();
        CompletableFuture[] futures = findPriceStream(product)
                .map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + (System.nanoTime() - start) / 1_000_000 + " msecs")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

    private static Stream<CompletableFuture<String>> findPriceStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice2(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));
    }

    public static void main(String[] args) {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Invocation returned after " + invocationTime + " msecs");

        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f\n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Price returned after " + retrievalTime + "msecs");
    }
}
