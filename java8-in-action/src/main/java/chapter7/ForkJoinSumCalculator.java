package chapter7;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

import static chapter7.ParallelStreamsHarness.FORK_JOIN_POOL;


/**
 * 1. 并行流内部使用了默认的ForkJoinPool，它默认的线程数量就是你的处理器数量，
 * 这个值是由Runtime.getRuntime().availableProcessors()得到的。
 *
 * 2. 分支/合并框架的目的是以递归方式将可以并行的任务拆分成更小的任务，然后将每个子任
 * 务的结果合并起来生成整体结果。它是ExecutorService接口的一个实现，它把子任务分配给
 * 线程池（称为ForkJoinPool）中的工作线程。
 *
 * 3. 要把任务提交到这个池，必须创建RecursiveTask<R>的一个子类，其中R是并行化任务
 * （以及所有子任务）产生的结果类型，或者如果任务不返回结果，则是RecursiveAction类型
 *
 * 4.使用分支/合并框架的最佳做法
 *    对一个任务调用join方法会阻塞调用方，直到该任务做出结果。因此，有必要在两个子
 * 任务的计算都开始之后再调用它。否则，你得到的版本会比原始的顺序算法更慢更复杂，
 * 因为每个子任务都必须等待另一个子任务完成才能启动。
 *    不应该在RecursiveTask内部使用ForkJoinPool的invoke方法。相反，你应该始终直
 * 接调用compute或fork方法，只有顺序代码才应该用invoke来启动并行计算。
 *    对子任务调用fork方法可以把它排进ForkJoinPool。同时对左边和右边的子任务调用
 * 它似乎很自然，但这样做的效率要比直接对其中一个调用compute低。这样做你可以为
 * 其中一个子任务重用同一线程，从而避免在线程池中多分配一个任务造成的开销
 *    分支/合并框架需要“预热”或者说要执行几遍才会被JIT编
 * 译器优化。这就是为什么在测量性能之前跑几遍程序很重要
 *
 * 5. 工作窃取机制
 *     分支/合并框架工程用一种称为工作窃取（work stealing）的技术来解决各个处理器工作不均匀。在实际应
 * 用中，这意味着这些任务差不多被平均分配到ForkJoinPool中的所有线程上。每个线程都为分
 * 配给它的任务保存一个双向链式队列，每完成一个任务，就会从##队列头##上取出下一个任务开始执
 * 行。基于前面所述的原因，某个线程可能早早完成了分配给它的所有任务，也就是它的队列已经
 * 空了，而其他的线程还很忙。这时，这个线程并没有闲下来，而是随机选了一个别的线程，从队
 * 列的##尾巴##上“偷走”一个任务。这个过程一直继续下去，直到所有的任务都执行完毕，所有的队
 * 列都清空。这就是为什么要划成许多小任务而不是少数几个大任务，这有助于更好地在工作线程
 * 之间平衡负载。
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    public static final long THRESHOLD = 10_000;

    private final long[] numbers;
    private final int start;
    private final int end;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    /**
     * 同时定义了将任务拆分成子任务的逻辑，以及无法再拆分或不方便再拆分时，生成
     * 单个子任务结果的逻辑。
     * @return
     */
    @Override
    protected Long compute() {
        int length = end - start;
        // 任务足够小或不可分,顺序计算该任务
        if (length <= THRESHOLD) {
            return computeSequentially();
        }

        /**
         * 将任务分成两个子任务递归调用本方法，拆分每个子任务，等待所有子任务完成合并每个子任务的结果
         */

        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2);

        // 利 用 另 一 个ForkJoinPool线程异步执行新创建的子任务
        leftTask.fork();
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);
        // 同步执行第二个子任务，有可能允许进一步递归划分
        Long rightResult = rightTask.compute();

        // 读取第一个子任务的结果，如果尚未完成就等待
        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return FORK_JOIN_POOL.invoke(task);
    }
}