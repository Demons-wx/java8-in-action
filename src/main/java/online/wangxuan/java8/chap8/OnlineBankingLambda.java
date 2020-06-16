package online.wangxuan.java8.chap8;

import java.util.function.Consumer;

/**
 * @author wangxuan
 * @date 2019/1/2 11:50 PM
 */

public class OnlineBankingLambda {

    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }
}
