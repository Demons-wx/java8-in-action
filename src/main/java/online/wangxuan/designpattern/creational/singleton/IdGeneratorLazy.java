package online.wangxuan.designpattern.creational.singleton;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangxuan
 * @date 2020/6/6 6:07 PM
 */

public class IdGeneratorLazy {

    private static AtomicInteger id = new AtomicInteger(0);
    private static IdGeneratorLazy INSTANCE;
    private IdGeneratorLazy() {}

    public static IdGeneratorLazy getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IdGeneratorLazy();
        }
        return INSTANCE;
    }

    public int getId() {
        return id.incrementAndGet();
    }
}
