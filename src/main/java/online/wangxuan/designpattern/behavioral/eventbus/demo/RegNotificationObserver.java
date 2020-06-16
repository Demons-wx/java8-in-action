package online.wangxuan.designpattern.behavioral.eventbus.demo;

import online.wangxuan.designpattern.behavioral.eventbus.Subscribe;

/**
 * @author wangxuan
 * @date 2020/5/23 4:02 PM
 */

public class RegNotificationObserver {

    @Subscribe
    public void handleRegSuccess(Long id) {
        System.out.println("email send success! userId = " + id);
    }
}
