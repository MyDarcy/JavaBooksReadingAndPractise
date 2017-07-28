package chapter14;

import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;

/**
 * 能满足下面任一要求就可以被称为高阶函数（higher-order function）：
 *   接受至少一个函数作为参数
 *   返回的结果是一个函数
 *
 *
 * 科里化是一种将具备2个参数（比如， x和y）的函数f转化为使用一个参数的函数g，并
 * 且这个函数的返回值也是一个函数，它会作为新函数的一个参数。后者的返回值和初始函数的
 * 返回值相同，即f(x,y) = (g(x))(y)。
 *
 * 可以将一个使用了6个参数的函数科里化成一个接受第2、 4、 6号参数，
 * 并返回一个接受5号参数的函数，这个函数又返回一个接受剩下的第1号和第3号参数的函数。
 */
public class Combinators {

    public static void main(String[] args) {
        System.out.println(repeat(3, (Integer x) -> 2 * x).apply(10));
        System.out.println(compose((Integer x) -> x * x, (Integer x) -> x / 3).apply(100));

        System.out.println(celcius(9.0 / 5, 32).apply(100.).intValue());

        // 柯里化
        System.out.println(g(100).applyAsInt(1));
    }

    static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, B> f) {
        return x -> g.apply(f.apply(x));
    }

    static <A> Function<A, A> repeat(int n, Function<A, A> f) {
        return n == 0 ? x -> x : compose(f, repeat(n - 1, f));
    }

    static  Function<Double, Double> celcius(double f, double base) {
        return (Double x) -> f * x + base;
    }

    static DoubleUnaryOperator celcius2(double f, double base) {
        return (x) -> f * x + base;
    }

    // f(x, y) -> (g(x))(y)
    static IntUnaryOperator g(int x) {
        return (y) -> x * x + y; // 返回的函数接受一个参数, 使用了外层函数传入的参数.
    }

}
