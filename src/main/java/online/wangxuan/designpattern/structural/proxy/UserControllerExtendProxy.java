package online.wangxuan.designpattern.structural.proxy;

/**
 * @author wangxuan
 * @date 2020/5/14 10:31 PM
 */

public class UserControllerExtendProxy extends UserController {

    private MetricsCollector metricsCollector;
    public UserControllerExtendProxy() {
        metricsCollector = new MetricsCollector();
    }

    @Override
    public UserVo login(String username, String password) {
        long startTime = System.currentTimeMillis();
        UserVo userVo = super.login(username, password);
        long endTime = System.currentTimeMillis();
        RequestInfo requestInfo = new RequestInfo("login", (endTime - startTime), startTime);
        metricsCollector.recordRequest(requestInfo);
        return userVo;
    }
}
