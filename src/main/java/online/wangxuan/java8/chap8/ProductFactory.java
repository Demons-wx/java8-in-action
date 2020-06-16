package online.wangxuan.java8.chap8;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 通常，你会创建一个工厂类，它包含一个负责实现不同对象的方法：
 * @author wangxuan
 * @date 2019/1/6 11:21 PM
 */

public class ProductFactory {

    public static Product createProduct(String name) {
        switch (name) {
            case "loan": return new Loan();
            case "stock": return new Stock();
            case "bond": return new Bond();
            default: throw new RuntimeException("No such product " + name);
        }
    }
}

class Product {

}

class Loan extends Product {

}

class Stock extends Product {

}

class Bond extends Product {

}

class ProductFactory2 {

    final static Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }

    public static Product createProduct(String name) {
        Supplier<Product> p = map.get(name);
        if (p != null) {
            return p.get();
        }
        throw new RuntimeException("No such product " + name);
    }
}