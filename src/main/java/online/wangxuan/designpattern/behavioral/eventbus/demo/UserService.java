package online.wangxuan.designpattern.behavioral.eventbus.demo;

import online.wangxuan.designpattern.behavioral.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangxuan
 * @date 2020/5/23 3:52 PM
 */

public class UserService {

    private EventBus eventBus;
    private static final int DEFAULT_POOL_SIZE = 20;
    private static Random random = new Random();

    public UserService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void setRegObservers(List<Object> observers) {
        observers.forEach(eventBus::register);
    }

    public Long register(String username, String password) {
        long userId = random.nextLong();
        System.out.println("register success! userId = " + userId);
        eventBus.post(userId);
        return userId;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
        UserService userService = new UserService(new EventBus(executorService));
        List<Object> observers = new ArrayList<>();
        observers.add(new RegNotificationObserver());
        observers.add(new RegPromotionObserver());
        userService.setRegObservers(observers);

        userService.register("wx", "123");
        executorService.shutdownNow();
    }
}
