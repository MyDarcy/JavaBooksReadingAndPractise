package chapter7;

import java.util.stream.*;

public class ParallelStreams {

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1) // iterate生成的是装箱的对象
                .limit(n)
                .reduce(Long::sum)
                .get();
    }

    /**
     * iterate生成的是装箱的对象，必须拆箱成数字才能求和；
     * 很难把iterate分成多个独立块来并行执行。
     *
     * 在现实中，对顺序流调用parallel方法并不意味着流本身有任何实际的变化。它
     * 在内部实际上就是设了一个boolean标志，表示你想让调用parallel之后进行的所有操作都并
     * 行执行。类似地，你只需要对并行流调用sequential方法就可以把它变成顺序流。
     *
     * 最后一次parallel或sequential调用会影响整个流水线(如果存在多次parallel或者sequential调用的时候)
     * @param n
     * @return
     */
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel() // 声明性地将顺序流变为并行流
                .reduce(Long::sum)
                .get();
    }

    /**
     * LongStream.rangeClosed直接产生原始类型的long数字，没有装箱拆箱的开销。
     * LongStream.rangeClosed会生成数字范围，很容易拆分为独立的小块。例如，范围1~20
     * 可分为1~5、 6~10、 11~15和16~20。
     * @param n
     * @return
     */
    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(Long::sum)
                .getAsLong();
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(Long::sum)
                .getAsLong();
    }

    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    /**
     * 每次访问total都会出现数据竞争。
     */
    public static class Accumulator {
        private long total = 0;

        public void add(long value) {
            total += value;
        }
    }
}
