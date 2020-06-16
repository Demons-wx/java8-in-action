package online.wangxuan.java8.chap8;

/**
 * @author wangxuan
 * @date 2019/1/2 10:59 PM
 */

public class Validator {

    private final ValidationStrategy strategy;

    public Validator(ValidationStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean validate(String s) {
        return strategy.execute(s);
    }
}
