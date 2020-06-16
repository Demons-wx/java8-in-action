package online.wangxuan.algo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xwangr
 * @date 2020/4/1
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final static int DEFAULT_CACHE_SIZE = 10;
    private int cacheSize;
    public LRUCache() {
        super(DEFAULT_CACHE_SIZE, 0.75f, true);
        this.cacheSize = DEFAULT_CACHE_SIZE;
    }

    public LRUCache(int cacheSize) {
        super(cacheSize, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > cacheSize;
    }
}
