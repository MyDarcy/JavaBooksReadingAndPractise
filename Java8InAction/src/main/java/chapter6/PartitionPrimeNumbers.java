package chapter6;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collector.Characteristics.*;

public class PartitionPrimeNumbers {

    public static void main(String... args) {
        System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimes(20));
        System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimesWithCustomCollector(100));

    }

    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(partitioningBy(candidate -> isPrime(candidate)));
    }

    public static boolean isPrime(int candidate) {
        return IntStream.rangeClosed(2, candidate - 1)
                .limit((long) Math.floor(Math.sqrt((double) candidate)) - 1)
                .noneMatch(i -> candidate % i == 0);
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(new PrimeNumbersCollector());
    }


    public static boolean isPrime(List<Integer> primes, Integer candidate) {
        double candidateRoot = Math.sqrt((double) candidate);
        return takeWhile(primes, i -> i <= candidateRoot).stream().noneMatch(i -> candidate % i == 0);
        //return primes.stream().takeWhile(i -> i <= candidateRoot).noneMatch(i -> candidate % i == 0);
    }

    public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
        int i = 0;
        for (A item : list) {
            if (!p.test(item)) { //只要求list中所有满足p的item, 也就是所有小于root的item(都是质数), 进一步比sqrt快了.
                return list.subList(0, i);
            }
            i++;
        }
        return list;
    }

    /**
     * public interface Collector<T, A, R>
     * 其中T、 A和R分别是流中元素的类型、用于累积部分结果的对象类型，以及collect操作最
     * 终结果的类型。这里应该收集Integer流，而累加器和结果类型则都是 Map<Boolean,
     * List<Integer>> 键是true和false，值则分别是质数和非质数的List
     */
    public static class PrimeNumbersCollector
            implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {
        /**
         * 创建累加器并进行初始化，为true和false两个键下面初始化了对应的空列表。
         * 在收集过程中会把质数和非质数分别添加到这里
         * @return
         */
        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier() {
            return () -> new HashMap<Boolean, List<Integer>>() {{
                put(true, new ArrayList<Integer>());
                put(false, new ArrayList<Integer>());
            }};
        }

        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
            return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
                acc.get(isPrime(acc.get(true), candidate)) // 根据isPrime的结果，获取质数或非质数列表
                        .add(candidate); // 将被测数添加到相应的列表中
            };
        }

        /**
         * 合并两个Map，即将第二个Map中质数和非质数列表中的所有数字合并到第一个Map的对应列表即可
         *
         * 本例子中这个收集器是不能并行使用的，因为该算法本身是顺序的。
         * 这意味着永远都不会调用combiner方法，你可以把它的实现留空
         * （更好的做法是抛出一个UnsupportedOperationException异常）
         * @return
         */
        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
            return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
                map1.get(true).addAll(map2.get(true));
                map1.get(false).addAll(map2.get(false));
                return map1;
            };
        }

        /**
         * accumulator正好就是收集器的结果，
         * 用不着进一步转换，那么finisher方法就返回identity函数
         * @return
         */
        @Override
        public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
            //  return i -> i; // 也可以
            return Function.identity();
        }

        /**
         * 既不是CONCURRENT也不是UNORDERED，但却是IDENTITY_FINISH的
         * @return
         */
        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
        }
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithInlineCollector(int n) {
        return Stream.iterate(2, i -> i + 1).limit(n)
                .collect(
                        () -> new HashMap<Boolean, List<Integer>>() {{
                            put(true, new ArrayList<Integer>());
                            put(false, new ArrayList<Integer>());
                        }},
                        (acc, candidate) -> {
                            acc.get(isPrime(acc.get(true), candidate))
                                    .add(candidate);
                        },
                        (map1, map2) -> {
                            map1.get(true).addAll(map2.get(true));
                            map1.get(false).addAll(map2.get(false));
                        });
    }
}
