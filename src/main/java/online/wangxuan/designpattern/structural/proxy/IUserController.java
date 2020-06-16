package online.wangxuan.designpattern.structural.proxy;

/**
 * 静态代理，基于接口
 * @author wangxuan
 * @date 2020/5/14 9:54 PM
 */

public interface IUserController {

    UserVo login(String username, String password);
    UserVo register(String username, String password);
}
