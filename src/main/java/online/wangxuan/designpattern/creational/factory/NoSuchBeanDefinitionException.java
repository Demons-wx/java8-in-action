package online.wangxuan.designpattern.creational.factory;

/**
 * @author wangxuan
 * @date 2020/5/12 11:29 PM
 */

public class NoSuchBeanDefinitionException extends RuntimeException {

    public NoSuchBeanDefinitionException(String message) {
        super(message);
    }
}
