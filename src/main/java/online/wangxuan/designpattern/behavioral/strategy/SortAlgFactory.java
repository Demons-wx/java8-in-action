package online.wangxuan.designpattern.behavioral.strategy;

import com.google.common.base.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangxuan
 * @date 2020/5/25 10:49 PM
 */

public class SortAlgFactory {

    private static final Map<String, ISortAlg> algs = new HashMap<>();

    static {
        algs.put("QuickSort", new QuickSort());
        algs.put("ExternalSort", new ExternalSort());
        algs.put("ConcurrentExternalSort", new ConcurrentExternalSort());
        algs.put("MapReduceSort", new MapReduceSort());
    }

    public static ISortAlg getSortAlg(String type) {
        if (Strings.isNullOrEmpty(type)) {
            throw new IllegalArgumentException("type should not be empty!");
        }
        return algs.get(type);
    }
}
