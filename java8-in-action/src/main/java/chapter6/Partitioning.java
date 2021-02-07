package chapter6;

import java.util.*;

import static chapter6.Dish.menu;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;


public class Partitioning {

    public static void main(String ... args) {
        System.out.println("Dishes partitioned by vegetarian: " + partitionByVegeterian());
        System.out.println("Vegetarian Dishes by type: " + vegetarianDishesByType());
        System.out.println("Most caloric dishes by vegetarian: " + mostCaloricPartitionedByVegetarian());
    }

    private static Map<Boolean, List<Dish>> partitionByVegeterian() {
        Map<Boolean, List<Dish>> collect = menu.stream().collect(partitioningBy(Dish::isVegetarian));
        return collect;
    }

    private static Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType() {
        Map<Boolean, Map<Dish.Type, List<Dish>>> collect = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                                groupingBy(Dish::getType)));
        return collect;
    }

    private static Object mostCaloricPartitionedByVegetarian() {
        Map<Boolean, Dish> collect = menu.stream().collect(
                partitioningBy(Dish::isVegetarian,
                        collectingAndThen(
                                maxBy(comparingInt(Dish::getCalories)),
                                Optional::get)));
        return collect;
    }
}

