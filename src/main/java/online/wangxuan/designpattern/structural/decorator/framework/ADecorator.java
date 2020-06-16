package online.wangxuan.designpattern.structural.decorator.framework;

/**
 *
 * 代理模式中，代理类附加的是跟原始类无关的功能，而在装饰器模式中，装饰器类附加的是跟原始类相关的增强功能。
 *
 * @author wangxuan
 * @date 2020/6/14 11:20 AM
 */

public class ADecorator implements IA {

    private IA ia;

    public ADecorator(IA ia) {
        this.ia = ia;
    }

    @Override
    public void f() {
        // 功能增强
        ia.f();
        // 功能增强
    }
}
