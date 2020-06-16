package online.wangxuan.designpattern.behavioral.eventbus.demo;

import online.wangxuan.designpattern.behavioral.eventbus.Subscribe;

/**
 * @author wangxuan
 * @date 2020/5/23 4:01 PM
 */

public class RegPromotionObserver {

    @Subscribe
    public void handleRegSuccess(Long userId) {
        System.out.println("优惠券发送成功! userId = " + userId);
    }
}
