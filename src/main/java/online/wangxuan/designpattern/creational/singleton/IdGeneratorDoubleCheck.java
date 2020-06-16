package online.wangxuan.designpattern.creational.singleton;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangxuan
 * @date 2020/6/6 6:12 PM
 */

public class IdGeneratorDoubleCheck {

    private static AtomicInteger id = new AtomicInteger(0);
    private static IdGeneratorDoubleCheck INSTANCE;
    private IdGeneratorDoubleCheck() {}

    public static IdGeneratorDoubleCheck getInstance() {
        if (INSTANCE == null) {
            synchronized (IdGeneratorDoubleCheck.class) {
                if (INSTANCE == null) {
                    INSTANCE = new IdGeneratorDoubleCheck();
                }
            }
        }
        return INSTANCE;
    }

    public int getId() {
        return id.incrementAndGet();
    }

}
