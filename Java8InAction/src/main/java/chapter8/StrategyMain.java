package chapter8;


/**
 * 策略模式代表了解决一类算法的通用解决方案，你可以在运行时选择使用哪种方案。
 */
public class StrategyMain {

    // 希望验证输入的内容是否根据标准进行了恰当的格式化
    public static void main(String[] args) {
        // old school
        Validator v1 = new Validator(new IsNumeric());
        System.out.println(v1.validate("aaaa"));
        Validator v2 = new Validator(new IsAllLowerCase ());
        System.out.println(v2.validate("bbbb"));


        // with lambdas
        // Lambda表达式实际已经对部分代码（或策略）进行了封装，而这就是创建策略设计模式的初衷。
        Validator v3 = new Validator((String s) -> s.matches("\\d+"));
        System.out.println(v3.validate("aaaa"));
        Validator v4 = new Validator((String s) -> s.matches("[a-z]+"));
        System.out.println(v4.validate("bbbb"));
    }

    // 函数式接口
    interface ValidationStrategy {
        public boolean execute(String s);
    }

    // 两个具体的实现
    static private class IsAllLowerCase implements ValidationStrategy {
        public boolean execute(String s){
            return s.matches("[a-z]+");
        }
    }
    static private class IsNumeric implements ValidationStrategy {
        public boolean execute(String s){
            return s.matches("\\d+");
        }
    }

    static private class Validator{
        private final ValidationStrategy strategy;
        public Validator(ValidationStrategy v){
            this.strategy = v;
        }
        public boolean validate(String s){
            return strategy.execute(s); }
    }
}
