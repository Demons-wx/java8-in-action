package online.wangxuan.designpattern.structural.proxy;

/**
 * @author wangxuan
 * @date 2020/5/14 9:56 PM
 */

public class UserController implements IUserController {

    @Override
    public UserVo login(String username, String password) {
        return new UserVo(username, password);
    }

    @Override
    public UserVo register(String username, String password) {
        return new UserVo(username, password);
    }
}
