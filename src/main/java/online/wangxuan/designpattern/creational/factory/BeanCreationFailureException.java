package online.wangxuan.designpattern.creational.factory;

/**
 * @author wangxuan
 * @date 2020/5/12 11:59 PM
 */

public class BeanCreationFailureException extends RuntimeException {
    public BeanCreationFailureException(ReflectiveOperationException e) {
    }
}
