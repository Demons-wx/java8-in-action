package online.wangxuan.designpattern.structural.proxy;

/**
 * @author wangxuan
 * @date 2020/5/14 9:56 PM
 */

public class UserVo {

    private String username;
    private String password;

    public UserVo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
