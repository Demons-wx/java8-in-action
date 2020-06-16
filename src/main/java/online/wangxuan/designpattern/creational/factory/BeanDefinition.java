package online.wangxuan.designpattern.creational.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxuan
 * @date 2020/5/12 11:16 PM
 */

public class BeanDefinition {

    private String id;
    private String className;
    private List<ConstructorArg> constructorArgs = new ArrayList<>();
    private Scope scope = Scope.SINGLETON;
    private boolean lazyInit = false;

    public BeanDefinition() {
    }

    public BeanDefinition(Scope scope, boolean lazyInit) {
        this.scope = scope;
        this.lazyInit = lazyInit;
    }

    public boolean isSingleton() {
        return scope.equals(Scope.SINGLETON);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Scope getScope() {
        return scope;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public List<ConstructorArg> getConstructorArgs() {
        return constructorArgs;
    }
}
