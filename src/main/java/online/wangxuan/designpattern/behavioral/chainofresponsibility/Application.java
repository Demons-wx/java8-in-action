package online.wangxuan.designpattern.behavioral.chainofresponsibility;

/**
 * @author wangxuan
 * @date 2020/5/25 11:38 PM
 */

public class Application {

    public static void main(String[] args) {
        HandlerChain chain = new HandlerChain();
        chain.addHandler(new AHandler());
        chain.addHandler(new BHandler());
        chain.handle();
    }
}
