package chapter6;

import java.util.IntSummaryStatistics;

import static chapter6.Dish.menu;
import static java.util.stream.Collectors.*;

/**
 * Author by darcy
 * Date on 17-7-26 下午9:39.
 * Description:
 */
public class SumReduceDemo {
    public static void main(String[] args) {
        // summingInt(ToIntFunction<? super T> mapper)　(T -> int)
        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println(totalCalories);

        double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));
        System.out.println(avgCalories);

        IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(menuStatistics);

        String shortMenu = menu.stream().map(Dish::getName).collect(joining());
        System.out.println(shortMenu);
        String menu2 = menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println(menu2);

        int totalCalories2 = menu.stream().collect(reducing(
                0, Dish::getCalories, (i, j) -> i + j));
        System.out.println(totalCalories2);

        int totalCalories11 = menu.stream()
                .collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
        System.out.println(totalCalories11);
        // 等价
        int totalCalories22 = menu.stream()
                .collect(reducing(0, Dish::getCalories, Integer::sum));
        System.out.println(totalCalories22);

        // 等价, 但是 orElse或orElseGet来解开Optional中包含的值更为安全。
        // reduce
        int totalCalories33 = menu.stream()
                .map(Dish::getCalories).reduce(Integer::sum).get();
        System.out.println(totalCalories33);
        // 等价
        int totalCalories44 = menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println(totalCalories44);
    }
}
