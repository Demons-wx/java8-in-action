package online.wangxuan.designpattern.structural.proxy;

/**
 * @author wangxuan
 * @date 2020/5/14 9:57 PM
 */

public class MetricsCollector {
    public void recordRequest(RequestInfo requestInfo) {
        System.out.println("代理动作: " + requestInfo);
    }
}
