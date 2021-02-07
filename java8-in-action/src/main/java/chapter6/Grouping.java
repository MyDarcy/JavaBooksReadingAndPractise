package chapter6;

import java.util.*;

import static chapter6.Dish.dishTags;
import static chapter6.Dish.menu;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;


public class Grouping {

    enum CaloricLevel {DIET, NORMAL, FAT}

    ;

    public static void main(String... args) {
        System.out.println("Dishes grouped by type: " + groupDishesByType());
        System.out.println("Dish names grouped by type: " + groupDishNamesByType());
/*        System.out.println("Dish tags grouped by type: " + groupDishTagsByType());
        System.out.println("Caloric dishes grouped by type: " + groupCaloricDishesByType());*/
        System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
        System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel());
        System.out.println("Count dishes in groups: " + countDishesInGroups());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByType());
        System.out.println("Most caloric dishes by type maxBy: " + mostCaloriesDishesByTypeWithMaxBy());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByTypeWithoutOprionals());
        System.out.println("Sum calories by type: " + sumCaloriesByType());
        System.out.println("Caloric levels by type: " + caloricLevelsByType());
    }

    private static Map<Dish.Type, List<Dish>> groupDishesByType() {
        return menu.stream().collect(groupingBy(Dish::getType));
    }

    private static Map<Dish.Type, List<String>> groupDishNamesByType() {
        return menu.stream().collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
    }
/*

    private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
        return menu.stream().collect(groupingBy(Dish::getType, flatMapping(dish -> dishTags.get( dish.getName() ).stream(), toSet())));
    }

    private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {
//        return menu.stream().filter(dish -> dish.getCalories() > 500).collect(groupingBy(Dish::getType));
        return menu.stream().collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));
    }
*/

    private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
        return menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));
    }

    private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> collect = menu.stream().collect(
                groupingBy(Dish::getType,
                        groupingBy((Dish dish) -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        })
                )
        );
        return collect;
    }

    private static Map<Dish.Type, Long> countDishesInGroups() {
        Map<Dish.Type, Long> collect = menu.stream()
                .collect(groupingBy(Dish::getType, counting()));
        return collect;
    }

    private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByType() {
        Map<Dish.Type, Optional<Dish>> collect = menu.stream().collect(
                groupingBy(Dish::getType,
                        reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
        return collect;
    }

    private static Map<Dish.Type, Optional<Dish>> mostCaloriesDishesByTypeWithMaxBy() {
        Map<Dish.Type, Optional<Dish>> collect = menu.stream()
                .collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
        return collect;
    }

    private static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOprionals() {
        Map<Dish.Type, Dish> collect = menu.stream().collect(
                groupingBy(Dish::getType,
                        collectingAndThen(
                                reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2),
                                Optional::get)));
        return collect;
    }


    private static Map<Dish.Type, Dish> mostCaloricDishesByTyepCollectingAndThenMaxBy(){
        Map<Dish.Type, Dish> collect = menu.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(
                                maxBy(comparingInt(Dish::getCalories)),
                                Optional::get)));
        return collect;
    }

    private static Map<Dish.Type, Integer> sumCaloriesByType() {
        Map<Dish.Type, Integer> collect = menu.stream().collect(groupingBy(Dish::getType,
                summingInt(Dish::getCalories)));
        return collect;
    }

    private static Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType() {
        Map<Dish.Type, Set<CaloricLevel>> collect = menu.stream().collect(
                groupingBy(Dish::getType, mapping(
                        dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        },
                        toSet())));
        return collect;
    }

    private static Map<Dish.Type, HashSet<CaloricLevel>> caloricLevelByType2() {
        Map<Dish.Type, HashSet<CaloricLevel>> collect = menu.stream().collect(
                groupingBy(Dish::getType, mapping(
                        dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }, toCollection(HashSet::new)
                ))
        );
        return collect;
    }
}
