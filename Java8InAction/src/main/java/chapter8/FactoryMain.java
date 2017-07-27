package chapter8;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 使用Java 8中的新特性达到了传统工厂模式同样的效果。但是，如果
 工厂方法createProduct需要接收多个传递给产品构造方法的参数，这种方式的扩展性不是很
 好。你不得不提供不同的函数接口，无法采用之前统一使用一个简单接口的方式。
 */
public class FactoryMain {

    public static void main(String[] args) {
        Product p1 = ProductFactory.createProduct("loan");

        // () -> T
        // Java 8中的新特性达到了传统工厂模式同样的效果
        Supplier<Product> loanSupplier = Loan::new;
        Product p2 = loanSupplier.get();

        Product p3 = ProductFactory.createProductLambda("loan");

    }

    static private class ProductFactory {
        public static Product createProduct(String name){
            switch(name){
                case "loan": return new Loan();
                case "stock": return new Stock();
                case "bond": return new Bond();
                default: throw new RuntimeException("No such product " + name);
            }
        }

        public static Product createProductLambda(String name){
            Supplier<Product> p = map.get(name);
            if(p != null) return p.get();
            throw new RuntimeException("No such product " + name);
        }
    }

    static private interface Product {}
    static private class Loan implements Product {}
    static private class Stock implements Product {}
    static private class Bond implements Product {}

    final static private Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        // 将产品名映射到对应的构造函数
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }
}
