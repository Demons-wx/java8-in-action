package online.wangxuan.java8.chap8;

import java.util.ArrayList;
import java.util.List;

/**
 * 首先，需要一个观察者接口，它将不同的观察者聚合在一起。它只有一个名为notify的方法，
 * 一旦接收到一条新闻，该方法就会被调用。
 * @author wangxuan
 * @date 2019/1/6 9:07 PM
 */

public interface Observer {
    void notify(String tweet);
}

/**
 * 声明不同的观察者，(这里是三家不同的报纸机构)，依据新闻中不同的关键字分别定义不同行为；
 */
class NYTimes implements Observer {
    @Override
    public void notify(String tweet) {
        if (tweet != null && tweet.contains("money")) {
            System.out.println("Breaking news in NY! " + tweet);
        }
    }
}

class Guardian implements Observer {
    @Override
    public void notify(String tweet) {
        if (tweet != null && tweet.contains("queen")) {
            System.out.println("Yet another news in London... " + tweet);
        }
    }
}

class LeMonde implements Observer {
    @Override
    public void notify(String tweet) {
        if (tweet != null && tweet.contains("wine")) {
            System.out.println("Today cheese, wine and news! " + tweet);
        }
    }
}

/**
 * Subject
 * 使用registerObserver方法可以注册一个新的观察者
 * 使用notifyObservers方法通知它的观察者一个新闻的到来
 */
interface Subject {
    void registerObserver(Observer o);
    void notifyObservers(String tweet);
}

class Feed implements Subject {

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void notifyObservers(String tweet) {
        observers.forEach(o -> o.notify(tweet));
    }
}