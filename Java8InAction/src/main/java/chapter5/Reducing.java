package chapter5;


import java.util.stream.*;
import java.util.*;

import static chapter5.Dish.menu;


public class Reducing{

    public static void main(String...args){

        List<Integer> numbers = Arrays.asList(3,4,5,1,2);
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        int sum2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum2);

        int max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
        System.out.println(max);

        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        min.ifPresent(System.out::println);

        int calories = menu.stream()
                           .map(Dish::getCalories)
                           .reduce(0, Integer::sum);
        System.out.println("Number of calories:" + calories);

        // Integer类有了一个静态的sum方法来对两个数求和
        int sum11 = numbers.stream().reduce(0, Integer::sum);

// reduce还有一个重载的变体，它不接受初始值，但是会返回一个Optional对象
        Optional<Integer> sum21 = numbers.stream().reduce((a, b) -> (a + b));
        sum21.ifPresent(System.out::println);

    }
}
