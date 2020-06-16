package online.wangxuan.designpattern.creational.singleton;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangxuan
 * @date 2020/6/6 6:20 PM
 */

public enum IdGeneratorEnum {
    INSTANCE;
    private AtomicInteger id = new AtomicInteger(0);
    public int getId() {
        return id.incrementAndGet();
    }
}
