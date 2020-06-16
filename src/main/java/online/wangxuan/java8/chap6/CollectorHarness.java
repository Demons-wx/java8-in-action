package online.wangxuan.java8.chap6;

import static online.wangxuan.java8.chap6.CollectorsTest.*;
/**
 * partitionPrimes - 367 ms
 * partitionPrimesWithCustomerCollector - 270 ms
 *
 * @author wangxuan
 * @date 2018/11/17 9:26 PM
 */

public class CollectorHarness {

    public static void main(String[] args) {
        long fastest = Long.MAX_VALUE;
        // 运行测试10次
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            // 将前100万个自然数按质数和非质数区分
            partitionPrimesWithCustomCollector(1_000_000);
            // 取运行时间的毫秒值
            long duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest) {
                fastest = duration;
            }
        }

        System.out.println("Fastest execution done in " + fastest + " ms");
    }
}
