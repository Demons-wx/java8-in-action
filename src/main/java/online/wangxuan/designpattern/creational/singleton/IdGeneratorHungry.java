package online.wangxuan.designpattern.creational.singleton;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangxuan
 * @date 2020/6/6 6:00 PM
 */

public class IdGeneratorHungry {
    private AtomicInteger id = new AtomicInteger(0);
    private static final IdGeneratorHungry INSTANCE = new IdGeneratorHungry();

    private IdGeneratorHungry() {}

    public static IdGeneratorHungry getInstance() {
        return INSTANCE;
    }

    public int getId() {
        return id.incrementAndGet();
    }
}
