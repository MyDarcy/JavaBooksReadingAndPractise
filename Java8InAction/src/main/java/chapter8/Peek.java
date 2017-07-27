package chapter8;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * peek的设计初衷就是在流的每个元素恢复运行之
 前，插入执行一个动作。但是它不像forEach那样恢复整个流的运行，而是在一个元素上完成操
 作之后，它只会将操作顺承到流水线中的下一个操作。
 */
public class Peek {

    public static void main(String[] args) {

        List<Integer> result = Stream.of(2, 3, 4, 5)
                .peek(x -> System.out.println("taking from stream: " + x)).map(x -> x + 17)
                .peek(x -> System.out.println("after map: " + x)).filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter: " + x)).limit(3)
                .peek(x -> System.out.println("after limit: " + x)).collect(toList());
        System.out.println(result);
    }
}
