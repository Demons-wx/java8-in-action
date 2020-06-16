package online.wangxuan.java8.chap12;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static java.time.temporal.TemporalAdjusters.*;
/**
 * @author wangxuan
 * @date 2019/1/29 10:03 PM
 */

public class DateTimeExamples {

    public static void main(String[] args) {

        /**
         * Java 8 为什么要引入新的日期和时间库
         *
         * 在Java1.0中，对日期和时间的支持只能依赖java.util.Date类。
         * - 这个类无法表示日期，只能以毫秒的精度表示时间。
         * - 年份的起始选择是1900年，月份的起始从0开始。
         * - Date类的toString返回值包含了JVM默认时区CET，即中欧时间(Central Europe Time)，这并不表示Date类在任何方面支持时区。
         *
         * Java1.1中，Date类中很多方法被废弃了，取而代之的是java.util.Calendar类。Calendar类也有类似的设计缺陷，更糟糕的
         * 是，同时存在Date和Calendar这两个类，也增加了程序员的困惑。到底该使用哪一个类呢？
         *
         * 此外，有的特性只在某一个类有提供，比如DateFormat方法就只在Date类里有。
         * DateFormat也有自己的问题。比如，它不是线程安全的，这意味着两个线程如果尝试使用同一个formatter解析日期，
         * 你可能会得到无法预期的结果。
         *
         * 这一章，我们一起探索新的日期和时间API所提供的新特性。
         * 我们从最基本的用例入手，比如创建同时适合人与机器的日期和时间，逐渐转入到API更高级的一些应用。
         */

        /************** 12.1 LocalDate、LocalTime、Instant、Duration以及Period **************/
        System.out.println("/************** 12.1 LocalDate、LocalTime、Instant、Duration以及Period **************/");
        System.out.println();

        // 1. 使用LocalDate和LocalTime

        // LocalDate是一个不可变对象，它只提供了简单的日期，并不含当天的时间信息。另外，它也不附带任何与时区相关的信息
        // LocalDate实例提供了多种方法来读取常用的值，创建一个LocalDate对象并读取其值：
        LocalDate date = LocalDate.of(2014, 3, 18);
        int year = date.getYear();
        Month month = date.getMonth();
        int day = date.getDayOfMonth();
        DayOfWeek dow = date.getDayOfWeek();
        int len = date.lengthOfMonth();
        boolean leap = date.isLeapYear();

        // 还可以使用工厂方法从系统时钟中获取当前的日期：
        LocalDate today = LocalDate.now();

        // 你还可以通过TemporalField参数给get方法拿到同样的信息：
        int year2 = date.get(ChronoField.YEAR);
        int month2 = date.get(ChronoField.MONTH_OF_YEAR);
        int day2 = date.get(ChronoField.DAY_OF_MONTH);

        // 创建LocalTime并读取其值：
        LocalTime time = LocalTime.of(13, 45, 20);
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();

        // LocalDate和LocalTime都可以通过解析代表它们的字符串创建：
        LocalDate date1 = LocalDate.parse("2014-03-18");
        LocalTime time1 = LocalTime.parse("13:45:20");

        // 你可以向parse方法传递一个DateTimeFormatter。该类的实例定义了如何格式化一个日期或者时间对象。

        // 2. 合并日期和时间

        // 这个复合类名叫LocalDateTime，是LocalDate和LocalTime的合体。它同时表示日期和时间，但不带有时区信息。
        // 直接创建LocalDateTime对象，或者通过合并日期和时间的方式创建：
        // 2014-03-18T13:45:20
        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20);
        LocalDateTime dt2 = LocalDateTime.of(date, time);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        LocalDateTime dt4 = date.atTime(time);
        LocalDateTime dt5 = time.atDate(date);

        // 你也可以从LocalDateTime中提取LocalDate或者LocalTime组件：
        LocalDate date2 = dt1.toLocalDate();
        LocalTime time2 = dt1.toLocalTime();

        // 3. 机器的日期和时间格式

        // 从计算机的角度来看，建模时间最自然的格式是表示一个持续时间段上某个点的单一大整型数。
        // 这也是java.time.Instant类对时间建模的方式，基本上是以Unix元年时间开始所经历的秒数进行计算。
        // 你可以通过向静态工厂方法ofEpochSecond传递一个代表秒数的值创建一个该类的实例。静态工厂方法
        // ofEpochSecond还有一个增强的重载版本，接收第二个以纳秒为单位的参数，对传入的作为秒的参数进行调整。
        Instant.ofEpochSecond(3);
        Instant.ofEpochSecond(3, 0);
        // 2秒之后再加上100万纳秒(1秒)
        Instant instant1 = Instant.ofEpochSecond(2, 1_000_000_000);
        // 4秒之前的100万纳秒
        Instant instant2 = Instant.ofEpochSecond(4, -1_000_000_000);

        // Instant类也支持静态工厂方法now，它能够帮你获取当前时刻的时间戳：
        Instant.now();

        // 4. 定义Duration或Period

        // 目前，我们看到的所有类都实现了Temporal接口，Temporal接口定义了如何读取和操作为时间建模的对象的值。
        // 很自然我们会想到，我们需要创建两个Temporal对象之间duration：
        Duration d1 = Duration.between(time1, time2);
        Duration d2 = Duration.between(dt1, dt2);
        Duration d3 = Duration.between(instant1, instant2);

        // 由于LocalDateTime和Instant是为不同的目的而设计的，一个是为了便于人阅读使用，另一个是为了便于机器处理，
        // 所以你不能将二者混用。如果你试图在这两类对象之间创建duration，会触发一个DateTimeException异常。
        // 此外，由于Duration类主要用于以秒和纳秒衡量时间长短。所以不能仅向between方法传递一个LocalDate对象作为参数。

        // Period类可以得到两个LocalDate之间的时常，如下所示：
        Period tenDays = Period.between(LocalDate.of(2014, 3, 8),
                LocalDate.of(2014, 3, 18));

        // 创建Duration和Period对象：
        Duration threeMinutes = Duration.ofMinutes(3);
        Duration threeMinutes2 = Duration.of(3, ChronoUnit.MINUTES);

        Period tenDays2 = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);

        /**
         * 截至目前，我们介绍的这些日期-时间对象都是不可修改的，这是为了更好的支持函数式编程，确保线程安全，
         * 保持领域模式一致性而做出的重大设计决定。
         *
         * 当然，新的日期和事件API也提供了一些便利的方法来创建这些对象的可变版本。
         *
         */

        /************** 12.2 操纵、解析和格式化日期 **************/
        System.out.println("/************** 12.2 操纵、解析和格式化日期 **************/");
        System.out.println();

        /**
         * 如果你已经有了一个LocalDate对象，想要创建它的一个修改版，最直接的方法是使用withAttribute方法。
         * withAttribute方法会创建对象的一个副本，并不会修改原来的对象！
         */
        // 以比较直观的方式操纵LocalDate的属性
        LocalDate date3 = LocalDate.of(2014, 3, 18); // 2014-03-18
        LocalDate date4 = date3.withYear(2011);                              // 2011-03-18
        LocalDate date5 = date4.withDayOfMonth(25);                          // 2011-03-25
        LocalDate date6 = date5.with(ChronoField.MONTH_OF_YEAR, 9); // 2011-09-25

        // 以相对方式修改LocalDate对象的属性
        LocalDate date7 = LocalDate.of(2014, 3, 18); // 2014-03-18
        LocalDate date8 = date7.plusWeeks(1);                                // 2014-03-25
        LocalDate date9 = date8.minusYears(3);                               // 2011-03-25
        LocalDate date10 = date9.plus(6, ChronoUnit.MONTHS);    // 2011-09-25

        // 1. 使用TemporalAdjuster

        /**
         * 有时候，你需要进行一些更加复杂的操作，比如，将日期调整到下个周日、下个工作日，或者是本月的最后一天。
         * 你可以使用重载版本的with方法，向其传递一个提供了更多定制化选择的TemporalAdjuster对象。
         */

        // 使用预定义的TemporalAdjuster
        LocalDate date11 = LocalDate.of(2014, 3, 18); // 2014-03-18
        LocalDate date12 = date11.with(nextOrSame(DayOfWeek.SUNDAY));         // 2014-03-23
        LocalDate date13 = date12.with(lastDayOfMonth());                     // 2014-03-31

        // 实现自己的TemporalAdjuster

        /**
         * 请设计一个NextWorkingDay类，该类实现了TemporalAdjuster接口，能够计算明天的额日期，同时过滤掉
         * 周六和周日这些节假日。格式如下：
         * date = date.with(new NextWorkingDay());
         *
         * {@link NextWorkingDay}
         */
         LocalDate date14 = date13.with(new NextWorkingDay());

         // 如果你想要使用Lambda表达式定义TemporalAdjuster对象，推荐使用TemporalAdjusters类的静态工厂方法ofDateAdjuster：
        TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(
            temporal -> {
                DayOfWeek dow10 = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                int dayToAdd = 1;
                if (dow == DayOfWeek.FRIDAY) {
                    dayToAdd = 3;
                } else if (dow == DayOfWeek.SATURDAY) {
                    dayToAdd = 2;
                }

                return temporal.plus(dayToAdd, ChronoUnit.DAYS);
            });

        LocalDate date15 = date13.with(nextWorkingDay);

        // 2. 打印输出及解析日期-时间对象

        /************** 12.3 处理不同的时区和历法 **************/
        System.out.println("/************** 12.3 处理不同的时区和历法 **************/");
        System.out.println();

    }
}






















