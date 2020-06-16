package online.wangxuan.java8.chap10;

import java.util.Optional;

/**
 * @author wangxuan
 * @date 2019/1/14 3:55 PM
 */

public class OptionalPerson {
    private Optional<OptionalCar> car;
    private int age;

    public int getAge() {
        return age;
    }

    public Optional<OptionalCar> getCar() {
        return car;
    }
}
