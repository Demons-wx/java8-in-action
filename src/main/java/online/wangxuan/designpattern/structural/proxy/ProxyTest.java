package online.wangxuan.designpattern.structural.proxy;

/**
 * @author wangxuan
 * @date 2020/5/14 10:03 PM
 */

public class ProxyTest {

    public static void main(String[] args) {
        // 基于接口的静态代理
        IUserController userController = new UserControllerProxy(new UserController());
        UserVo user = userController.login("root", "123456");
        System.out.println(user);

        // 继承方式的静态代理
        IUserController userController1 = new UserControllerExtendProxy();
        UserVo userVo = userController1.login("admin", "123456");
        System.out.println(userVo);

        // 动态代理
        MetricsCollectorProxy proxy = new MetricsCollectorProxy();
        IUserController controller = (IUserController) proxy.createProxy(new UserController());
        System.out.println(controller.register("dynamic", "123456"));
    }
}
