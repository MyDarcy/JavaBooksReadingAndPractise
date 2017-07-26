package chapter5;



import java.util.*;
import java.util.stream.Stream;

import static chapter5.Dish.menu;
import static java.util.stream.Collectors.toList;


public class Mapping{

    public static void main(String...args){

        // map
        List<String> dishNames = menu.stream()
                                     .map(Dish::getName)
                                     .collect(toList());
        System.out.println(dishNames);

        // map
        List<String> words = Arrays.asList("Hello", "World");
        List<Integer> wordLengths = words.stream()
                                         .map(String::length)
                                         .collect(toList());
        System.out.println(wordLengths);

        List<String[]> list = words.stream()
                .map(str -> str.split("\\s+"))
                .distinct()
                .collect(toList());

        List<Stream<String>> streamList = words.stream()
                .map(str -> str.split("\\s+"))
                .map(Arrays::stream)
                .distinct()
                .collect(toList());

        List<String> stringList = words.stream()
                .flatMap(str -> Arrays.stream(str.split("\\s+")))
                .distinct()
                .collect(toList());

        // flatMap
        words.stream()
                 .flatMap((String line) -> Arrays.stream(line.split("")))
                 .distinct()
                 .forEach(System.out::println);

        // flatMap
        List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
        List<Integer> numbers2 = Arrays.asList(6,7,8);
        List<int[]> pairs = numbers1.stream()
                .flatMap((Integer i) -> numbers2.stream()
                                .map((Integer j) -> new int[]{i, j}))
                .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                .collect(toList());
        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));


        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares =
                numbers.stream()
                        .map(n -> n * n)
                        .collect(toList());
        System.out.println(squares);

        List<Integer> numbers11 = Arrays.asList(1, 2, 3);
        List<Integer> numbers22 = Arrays.asList(3, 4);
        List<int[]> pairs1 =  numbers1.stream()
                .flatMap(i -> numbers2.stream()
                                .map(j -> new int[]{i, j})) // 每个i都映射为一个Stream<int[]>, 需要将这多个Stream<int[]>合并为一个Stream<int[]>
                .collect(toList());
    }
}
