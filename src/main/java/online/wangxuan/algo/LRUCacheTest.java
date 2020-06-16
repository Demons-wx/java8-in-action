package online.wangxuan.algo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author xwangr
 * @date 2020/4/1
 */
public class LRUCacheTest {

    private static LRUCache<Integer, Integer> cache = new LRUCache<>(3);

    public static void main(String[] args) throws InterruptedException {
        cache.put(1, 2);
        cache.put(2, 3);
        cache.put(3, 4);
        cache.get(1);
        cache.put(4, 5);

        System.out.println(cache.size());
        System.out.println(cache.get(2));

        Map<Integer, Integer> map = new HashMap<>(16);
        for (int i = 0; i < 12; i++) {
            map.put(i, i);
        }
        // 调试resize()过程: 初始capacity=16, threshold=12, resize会扩容2倍,
        // the elements from each bin must either stay at same index, or move
        // with a power of two offset in the new table
        map.put(12, 12);

        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(10);
        for (int i = 0; i < 5; i++) {
            q.offer(i);
        }
        for (int i = 0; i < 5; i++) {
            q.poll();
        }

        q.take();
    }

}
