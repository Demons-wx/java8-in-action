package online.wangxuan.designpattern.behavioral.chainofresponsibility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxuan
 * @date 2020/5/25 11:36 PM
 */

public class HandlerChain {

    private List<IHandler> handlers = new ArrayList<>();

    public void addHandler(IHandler handler) {
        handlers.add(handler);
    }

    public void handle() {
        for (IHandler handler : handlers) {
            boolean handled = handler.handle();
            if (handled) return;
        }
    }
}
