package online.wangxuan.java8.chap8;

/**
 * @author wangxuan
 * @date 2019/1/2 10:56 PM
 */

public class IsAllLowerCase implements ValidationStrategy {
    @Override
    public boolean execute(String s) {
        return s.matches("[a-z]+");
    }
}
