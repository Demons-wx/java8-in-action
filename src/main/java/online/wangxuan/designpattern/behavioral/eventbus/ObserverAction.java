package online.wangxuan.designpattern.behavioral.eventbus;

import com.google.common.base.Preconditions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wangxuan
 * @date 2020/5/23 11:50 AM
 */

public class ObserverAction {

    private Object target;  // 观察者类
    private Method method;  // 观察者方法

    public ObserverAction(Object target, Method method) {
        this.target = Preconditions.checkNotNull(target);
        this.method = method;
        this.method.setAccessible(true);
    }

    // 执行观察者方法
    public void execute(Object event) {
        try {
            method.invoke(target, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
