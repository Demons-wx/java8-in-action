package online.wangxuan.designpattern.creational.factory;

/**
 * @author wangxuan
 * @date 2020/5/12 11:07 PM
 */

public interface ApplicationContext {
    Object getBean(String beanId);
}
