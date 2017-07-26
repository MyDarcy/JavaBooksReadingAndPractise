package chapter5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * Author by darcy
 * Date on 17-7-26 下午2:15.
 * Description:
 */
public class MyAnswers {

    public static void main(String... args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        // 找出2011年的所有交易并按交易额排序（从低到高）
        List<Transaction> transactionList = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());
        System.out.println(transactionList);


        // 交易员都在哪些不同的城市工作过
        List<String> cities = transactions.stream()
                .map(t -> t.getTrader())
                .map(trader -> trader.getCity()) // 两个可以合并为一个.
                .distinct()
                .collect(toList()); // toSet()更简洁, 去掉distinct()方法．
        System.out.println(cities);

        //　查找所有来自于剑桥的交易员，并按姓名排序
        List<Trader> traders = transactions.stream()
                .map(transaction -> transaction.getTrader()) // map(Transaction::getTrader)
                .filter(trander -> trander.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());

        System.out.println(traders);

        // 返回所有交易员的姓名字符串，按字母顺序排序
        String nameString = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (a, b) -> a + b);// collect(joining()); better.
        System.out.println(nameString);

        // 有没有交易员是在米兰工作的
        boolean anyMilan = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(anyMilan);

        // 打印生活在剑桥的每个交易员的交易额
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(transaction -> transaction.getValue()) // map(Transaction::getValue)
                .forEach(System.out::println);


        // 所有交易中，最高的交易额是多少
        Optional<Integer> maxValue = transactions.stream()
                .map(transaction -> transaction.getValue()) // 等价于 map(Transaction::getValue)
                .reduce(Integer::max);
        maxValue.ifPresent(System.out::println);

        // 找到交易额最小的交易
        transactions.stream()
                .reduce((t1, t2) -> (t1.getValue() < t2.getValue()) ? t1 : t2);
        // 等价于
        transactions.stream()
                .min(comparing(Transaction::getValue));

    }
}
