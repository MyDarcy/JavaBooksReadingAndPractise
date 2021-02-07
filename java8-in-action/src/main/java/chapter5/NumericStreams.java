package chapter5;


import java.util.stream.*;
import java.util.*;

import static chapter5.Dish.menu;


public class NumericStreams{

    public static void main(String...args){
    
        List<Integer> numbers = Arrays.asList(3,4,5,1,2);

        Arrays.stream(numbers.toArray()).forEach(System.out::println);
        int calories = menu.stream()
                           .mapToInt(Dish::getCalories)
                           .sum();
        System.out.println("Number of calories:" + calories);


        // max and OptionalInt
        OptionalInt maxCalories = menu.stream()                                                      
                                      .mapToInt(Dish::getCalories)
                                      .max();

        int max;
        if(maxCalories.isPresent()){
            max = maxCalories.getAsInt();
        }
        else {
            // we can choose a default value
            max = 1;
        }
        System.out.println(max);

        // numeric ranges
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                                 .filter(n -> n % 2 == 0);

        System.out.println("Count:" + evenNumbers.count());

        Stream<int[]> pythagoreanTriples =  IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100) // 多个Stream<Integer[]>扁平化为一个Stream<Integer[]>
                        .filter(b -> Math.sqrt(a*a + b*b) % 1 == 0) //IntStream
                        .boxed() // Stream<Integer>
                        .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})); // 多个Stream<Integer[]>

        pythagoreanTriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));


        Stream<int[]> triples = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0) // 仍然得到的是IntStream
                        .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}));// IntStream的mapToObj方法来改写它, 来得到一个Stream<int[]>流
        System.out.println("-----------");

        triples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));


        Stream<double[]> triples2 = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(array -> array[2] % 1 == 0));
        System.out.println("-----");
        triples2.forEach(t -> System.out.println((int) t[0] + ", " + (int) t[1] + ", " + (int) t[2]));
    }
   
    public static boolean isPerfectSquare(int n){
        return Math.sqrt(n) % 1 == 0;
    }

}
