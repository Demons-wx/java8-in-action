package online.wangxuan.java8.chap5;

/**
 * @author wangxuan
 * @date 2018/11/11 12:52 PM
 */

public class Trader {

    private final String name;
    private final String city;

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Trader: " + this.name + "in " + this.city;
    }
}
