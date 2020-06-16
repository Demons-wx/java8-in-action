package online.wangxuan.java8.chap5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangxuan
 * @date 2018/11/11 12:56 PM
 */

public class PuttingIntoPractice {

    public static void main(String[] args) {

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

        // 1. 找出2011年发生的所有交易，并按交易额排序(从低到高)
        System.out.println("1. 找出2011年发生的所有交易，并按交易额排序(从低到高)");
        transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList()).forEach(System.out::println);

        // 2. 交易员都在哪些不同的城市工作过?
        System.out.println("2. 交易员都在哪些不同的城市工作过?");
        transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList())
                .forEach(System.out::println);

        // 3. 查找所有来自剑桥的交易员，并按姓名排序
        System.out.println("3. 查找所有来自剑桥的交易员，并按姓名排序");
        transactions.stream()
                .map(Transaction::getTrader)
                .distinct()
                .filter(t -> t.getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList())
                .forEach(System.out::println);

        // 4. 返回所有交易员的姓名字符串，按字母顺序排序
        System.out.println("4. 返回所有交易员的姓名字符串，按字母顺序排序");
        System.out.println(transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2));


        // 5. 有没有交易员是在米兰工作的？
        System.out.println("5. 有没有交易员是在米兰工作的？");
        System.out.println(transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Milan")));

        // 6. 打印生活在剑桥的交易员的所有交易额。
        System.out.println("6. 打印生活在剑桥的交易员的所有交易额");
        System.out.println(transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(0, (a, b) -> a + b));

        // 7. 所有交易中，最高的交易额是多少？
        System.out.println("7. 所有交易中，最高的交易额是多少？");
        System.out.println(transactions.stream()
                .sorted((t1, t2) -> t2.getValue() - t1.getValue())
                .findFirst().get().getValue());

        System.out.println(transactions.stream()
                .map(Transaction::getValue)
                .reduce(0, (v1 ,v2) -> v1 >= v2 ? v1 : v2));

        // 8. 找到交易额最小的交易。
        System.out.println("8. 找到交易额最小的交易。");
        System.out.println(transactions.stream()
                .sorted(Comparator.comparing(Transaction::getValue))
                .findFirst().get());

        transactions.stream()
                .min(Comparator.comparing(Transaction::getValue));

    }
}
