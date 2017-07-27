package chapter8;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * 责任链模式是一种创建处理对象序列（比如操作序列）的通用方案。一个处理对象可能需要
 在完成一些工作之后，将结果传递给另一个对象，这个对象接着做一些工作，再转交给下一个处
 理对象，以此类推。
 */
public class ChainOfResponsibilityMain {

    public static void main(String[] args) {
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setSuccessor(p2);
        String result1 = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result1);

        /**
         * public interface UnaryOperator<T> extends Function<T, T>
         */

        /**
         * public interface BinaryOperator<T> extends BiFunction<T,T,T>
         *
         * BinaryOperator
         */

         // T -> T
        UnaryOperator<String> headerProcessing =
                (String text) -> "From Raoul, Mario and Alan: " + text;

        // T -> R
        Function<String, String> stringObjectFunction =
                (String text) -> "From Raoul, Mario and Alan: " + text;


        /**
         *
         default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (T t) -> after.apply(apply(t));
         }
         */
        UnaryOperator<String> spellCheckerProcessing =
                (String text) -> text.replaceAll("labda", "lambda");
        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
        String result2 = pipeline.apply("Aren't labdas really sexy?!!");
        System.out.println(result2);
    }

    // 定义代表处理对象的抽象类
    static private abstract class ProcessingObject<T> {
        // 定义一个字段来记录后续对象。
        protected ProcessingObject<T> successor;

        public void setSuccessor(ProcessingObject<T> successor) {
            this.successor = successor;
        }

        public T handle(T input) {
            T r = handleWork(input);
            if (successor != null) {
                return successor.handle(r);
            }
            return r;
        }

        abstract protected T handleWork(T input);
    }

    static private class HeaderTextProcessing
            extends ProcessingObject<String> {
        public String handleWork(String text) {
            return "From Raoul, Mario and Alan: " + text;
        }
    }

    static private class SpellCheckerProcessing
            extends ProcessingObject<String> {
        public String handleWork(String text) {
            return text.replaceAll("labda", "lambda");
        }
    }
}


