package online.wangxuan.java8.chap8;

import lombok.Data;

/**
 * @author wangxuan
 * @date 2019/1/2 11:31 PM
 */

public abstract class OnlineBanking {

    public void processCustomer(int id) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy(c);
    }

    abstract void makeCustomerHappy(Customer c);
}

@Data
class Customer {
    String name;
}

class Database {

    public static Customer getCustomerWithId(int id) {
        return new Customer();
    }
}
