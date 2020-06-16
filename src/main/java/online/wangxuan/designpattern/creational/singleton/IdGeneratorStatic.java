package online.wangxuan.designpattern.creational.singleton;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangxuan
 * @date 2020/6/6 6:16 PM
 */

public class IdGeneratorStatic {

    private static AtomicInteger id = new AtomicInteger(0);
    private IdGeneratorStatic() {}

    public static IdGeneratorStatic getInstance() {
        return IdGeneratorHolder.INSTANCE;
    }

    public int getId() {
        return id.incrementAndGet();
    }

    private static class IdGeneratorHolder {
        private static final IdGeneratorStatic INSTANCE = new IdGeneratorStatic();
    }
}
