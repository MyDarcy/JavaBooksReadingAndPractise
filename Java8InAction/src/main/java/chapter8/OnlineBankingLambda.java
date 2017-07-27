package chapter8;

import java.util.function.Consumer;

/**
 * 果你需要采用某个算法的框架，同时又希望有一定的灵活度，能对它的某些部分进行改进，
 那么采用模板方法设计模式是比较通用的方案。模板方法模式在你
 “希望使用这个算法，但是需要对其中的某些行进行改进，才能达到希望的效果”时是非常有用的。
 */
public class OnlineBankingLambda {

    public static void main(String[] args) {
        new OnlineBankingLambda().processCustomer(1337, (Customer c) -> System.out.println("Hello!"));
    }

    // Consumer
    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy){
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }

    // dummy Customer class
    static private class Customer {}
    // dummy Database class
    static private class Database{
        static Customer getCustomerWithId(int id){ return new Customer();}
    }
}
