package online.wangxuan.java8.chap9;

import java.util.List;

/**
 * @author wangxuan
 * @date 2019/1/13 6:31 PM
 */

public class Utils {
    public static void paint(List<Resizable> l) {
        l.forEach(r -> {
            r.setAbsoluteSize(42, 42);
            r.draw();
        });
    }
}
