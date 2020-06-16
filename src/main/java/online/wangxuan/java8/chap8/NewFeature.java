package online.wangxuan.java8.chap8;

import online.wangxuan.java8.chap4.Dish;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * @author wangxuan
 * @date 2018/12/9 10:54 PM
 */

public class NewFeature {

    public static void main(String[] args) {

        /************** 8.1 为改善可读性和灵活性重构代码 **************/
        System.out.println("/************** 8.1 为改善可读性和灵活性重构代码 **************/");
        System.out.println();

        // 8.1.1 改善代码的可读性
        // - 重构代码，用Lambda表达式取代匿名类
        // - 用方法引用重构Lambda表达式
        // - 用Stream API重构命令式的数据处理

        // 8.1.2 从匿名类到Lambda表达式

        // 传统方式，使用匿名类
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };

        // 新方式，使用Lambda表达式
        Runnable r2 = () -> System.out.println("Hello");

        // 匿名类和Lambda表达式中this和super的含义是不同的。
        // 在匿名类中，this代表的是类自身，但是在Lambda中，它代表的是包含类。
        // 其次，匿名类可以屏蔽包含类的变量，而Lambda表达式不能(会导致编译错误)，如下：
        //        int a = 10;
        //        Runnable r3 = () -> {
        //            int a = 2;  // 编译错误
        //            System.out.println(a);
        //        };
        //
        //        Runnable r4 = new Runnable() {
        //            @Override
        //            public void run() {
        //                int a = 2; // 一切正常
        //                System.out.println(a);
        //            }
        //        };

        // 8.1.3 从Lambda表达式到方法引用的转换

        // Lambda表达式非常适用于需要传递代码片段的场景。不过，为了改善代码的可读性，也请尽量使用方法引用。
        // 因为方法名往往能更直观的表达代码的意图。

        // 很多通用的归约操作，都有内建的辅助方法可以和方法引用结合使用。
        // 与其编写：
        int totalCalories = Dish.menu.stream()
                .map(Dish::getCalories)
                .reduce(0, (c1, c2) -> c1 + c2);

        // 不如尝试使用内置的集合类：
        totalCalories = Dish.menu.stream()
                .collect(Collectors.summingInt(Dish::getCalories));

        totalCalories = Dish.menu.stream()
                .mapToInt(Dish::getCalories).sum();

        // 8.1.4 从命令式的数据处理切换到Stream

        // 我们建议你将所有的使用迭代器这种数据处理模式处理集合的代码都转换成Stream API的方式。

        // 比如下面的命令式代码使用了两种模式：筛选和抽取，这两种模式被混在一起，这样代码结构迫使程序员必须彻底
        // 清楚程序的每个细节才能理解代码功能。
        List<String> dishNames = new ArrayList<>();
        for (Dish dish : Dish.menu) {
            if (dish.getCalories() > 300) {
                dishNames.add(dish.getName());
            }
        }

        // 替代方案使用Stream API, 采用这种方式代码读起来更像是陈述问题，并行化也非常容易：
        dishNames = Dish.menu.parallelStream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .collect(Collectors.toList());

        // 不幸的是，将命令式代码结构转换为Stream API的形式是个困难的任务，因为你需要考虑控制流语句，比如break、
        // continue、return，并选择使用恰当的流操作。好消息是已经有一些工具可以帮助我们完成这个任务：
        // http://refactoring.info/tools/LambdaFicator/

        // 8.1.5 增加代码的灵活性

        // 哪些模式可以马上应用到你的代码中，让你享受Lambda表达式带来的便利。
        // 1. 采用函数接口
        // 2. 有条件的延迟执行
        // 3. 环绕执行

        /************** 8.2 使用Lambda重构面向对象的设计模式 **************/
        System.out.println("/************** 8.2 使用Lambda重构面向对象的设计模式 **************/");
        System.out.println();

        // 使用Lambda后，很多现存的略显臃肿的设计模式能够用更精简的方式实现了。这一节，我们会针对5个设计模式展开讨论：
        // - 策略模式
        // - 模板方法
        // - 观察者模式
        // - 责任链模式
        // - 工厂模式

        // 8.2.1 策略模式

        // 策略模式代表了解决一类算法的通用解决方案，你可以在运行时选择使用哪种方案。
        // 比如使用不同的标准来验证输入的有效性，使用不同方式来分析或者格式化输入。

        // 策略模式包含三部分内容：
        // - 一个代表某个算法的接口(它是策略模式的接口)
        // - 一个或多个该接口的具体实现，它们代表了算法的多种实现(比如，实体类ConcreteStrategyA或ConcreteStrategyB)
        // - 一个或多个使用策略对象的客户

        // 假设你希望验证输入的内容是否根据标准进行了恰当的格式化，比如只包含小写字母或数字：
        Validator numericValidator = new Validator(new IsNumeric());
        boolean b1 = numericValidator.validate("aaaa");
        Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
        boolean b2 = lowerCaseValidator.validate("bbbb");

        // 使用Lambda表达式
        // 我们不用声明新的类来实现不同的策略，通过直接传递Lambda表达式就能达到同样的目的，并且更加简洁：
        Validator numericValidator2 = new Validator((String s) -> s.matches("[a-z]+"));
        boolean b11 = numericValidator2.validate("aaaa");
        Validator lowerCaseValidate = new Validator((String s) -> s.matches("\\d+"));
        boolean b22 = lowerCaseValidate.validate("bbbb");

        // 8.2.2 模板方法

        // 模板方法模式在你"希望使用这个算法，但是需要对其中的某些行为进行改进，才能达到希望的效果"时非常有用
        // 假设你需要编写一个简单地在线银行应用。通常，用户需要输入一个用户账户，之后应用才能从银行的数据库中
        // 得到用户的详细信息，最终完成一些让用户满意的操作。不同分行的在线银行应用让客户满意的方式略有不同，
        // 比如给客户的账户发放红利，或者仅仅是少发送一些推广文件。

        /**
         * {@link OnlineBanking}
         * processCustomer方法搭建了在线银行算法的框架：获取客户提供的ID，然后提供服务让用户满意。
         * 不同支行可以通过继承OnlineBanking类，对该方法提供差异化实现。
         */

        // 使用Lambda表达式
        // 创建算法框架，让具体地实现插入某些部分。我们向processCustomer方法引入第二个参数：
        /**
         * {@link OnlineBankingLambda}
         */
        // processCustomer(int id, Consumer<Customer> makeCustomerHappy);
        // 现在，可以直接插入不同的行为，不再需要继承OnlineBanking类了：
        new OnlineBankingLambda().processCustomer(1337, (Customer c) -> {
            System.out.println("Hello " + c.getName());
        });

        // 8.2.3 观察者模式

        // 观察者模式是一种比较常见的方案，某些事件发生时(比如状态改变)，如果一个对象(通常我们称之为主题)需要自动的通知
        // 其他多个对象(称为观察者)。就会采用该方案。

        // 示例：你需要为Twitter这样的应用设计并实现一个定制化的通知系统。想法很简单：好几家报纸机构都订阅了新闻，他们希望
        // 当接收的新闻中包含他们感兴趣的关键字时，能得到通知。

        /**
         * {@link Observer}
         */

        Feed f = new Feed();
        f.registerObserver(new NYTimes());
        f.registerObserver(new Guardian());
        f.registerObserver(new LeMonde());

        f.notifyObservers("The queen said her favourite book is Java 8 in Action !");

        // 使用Lambda表达式

        // 使用Lambda表达式后，你无需显示的实例化三个观察者对象，直接传递Lambda表达式表示需要执行的行为即可：
        f.registerObserver((String tweet) -> {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY! " + tweet);
            }
        });

        f.registerObserver((String tweet) -> {
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Yet another news in London... " + tweet);
            }
        });

        // 8.2.4 责任链模式

        // 责任链模式是一种创建处理对象序列的通用方案。一个处理对象可能需要在完成一些工作之后，
        // 将结果传递给另一个对象，这个对象接着做一些工作，再转交给下一个处理对象，以此类推。

        /**
         * {@link ProcessingObject}
         */
        // 现在就可以将这两个处理对象结合起来，构造一个操作序列：
        ProcessingObject<String> p1 = new HandleTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();

        p1.setSuccessor(p2);

        String result = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result);

        // 使用Lambda表达式
        // 你可以将处理对象作为函数的一个实例，或者更确切的说作为UnaryOperator<String>的一个实例。
        // 为了链接这些函数，你需要使用andThen方法。

        UnaryOperator<String> headerProcessing =
                (String text) -> "From Raoul, Mario and Alan: " + text;

        UnaryOperator<String> spellCheckerProcessing =
                (String text) -> text.replaceAll("labda", "lambda");

        Function<String, String> pipeline =
                headerProcessing.andThen(spellCheckerProcessing);

        String result2 = pipeline.apply("Aren't labdas really sexy?!!");
        System.out.println(result2);

        // 8.2.5 工厂模式

        // 使用工厂模式，你无需向客户端暴露实例化的逻辑就能完成对象的创建。
        // 比如，我们假定你为一家银行工作，他们需要一种方式创建不同的金融产品：贷款、期权、股票等等。

        /**
         * {@link ProductFactory}
         */
        // 你在创建对象时不用再担心会将构造函数或者配置暴露给客户，这使得客户创建产品时更加简单：
        Product p = ProductFactory.createProduct("loan");

        // 使用Lambda表达式

        // 下面是一个引用贷款构造函数的示例：
        Supplier<Product> loanSupplier = Loan::new;
        Loan loan = (Loan) loanSupplier.get();

        // 通过这种方式重构之前的代码，创建一个map，将产品映射到对应的构造函数
        /**
         * {@link ProductFactory2}
         */

        /************** 8.3 测试Lambda表达式 **************/
        System.out.println("/************** 8.3 测试Lambda表达式 **************/");
        System.out.println();

        // 使用日志调试
        // 假如你试图对流操作中的流水线进行调试，该如何入手呢？
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5);

        // 使用forEach将流操作的结果日志输出？
        numbers.stream()
                .map(x -> x + 17)
                .filter(x -> x % 2 == 0)
                .limit(3)
                .forEach(System.out::println);

        // 一旦调用forEach，整个流就会恢复运行。并不能帮助我们理解Stream流水线中的每个操作。

        // peek: 在流的每个元素恢复运行之前，插入执行一个动作。
        List<Integer> result3 =
                numbers.stream()
                        // 输出来自数据源的当前元素值
                        .peek(x -> System.out.println("from stream: " + x))
                        .map(x -> x + 17)
                        // 输出map操作的结果
                        .peek(x -> System.out.println("after map: " + x))
                        .filter(x -> x % 2 == 0)
                        // 输出经过filter操作之后，剩下的元素个数
                        .peek(x -> System.out.println("after filter: " + x))
                        .limit(3)
                        // 输出经过limit操作之后，剩下的元素个数
                        .peek(x -> System.out.println("after limit: " + x))
                        .collect(Collectors.toList());

    }
}







































