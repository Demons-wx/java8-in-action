package online.wangxuan.designpattern.structural.proxy;

/**
 * @author wangxuan
 * @date 2020/5/14 9:57 PM
 */

public class UserControllerProxy implements IUserController {

    private MetricsCollector metricsCollector;
    private UserController userController;

    public UserControllerProxy(UserController userController) {
        this.userController = userController;
        metricsCollector = new MetricsCollector();
    }

    @Override
    public UserVo login(String username, String password) {
        long startTimestamp = System.currentTimeMillis();

        // 委托
        UserVo userVo = userController.login(username, password);
        long endTime = System.currentTimeMillis();
        RequestInfo requestInfo = new RequestInfo("login", (endTime - startTimestamp), startTimestamp);
        metricsCollector.recordRequest(requestInfo);
        return userVo;
    }

    @Override
    public UserVo register(String username, String password) {
        return null;
    }
}
