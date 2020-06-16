package online.wangxuan.java8.chap10;

import java.util.*;

/**
 * @author wangxuan
 * @date 2019/1/13 9:34 PM
 */

public class OptionalDemo {

    public static void main(String[] args) {

        /************** 10.1 如何为缺失的值建模 **************/
        System.out.println("/************** 10.1 如何为缺失的值建模 **************/");
        System.out.println();

        /**
         * {@link Person}
         * {@link Car}
         * {@link Insurance}
         */
        OptionalDemo demo = new OptionalDemo();
        Person person = new Person();
        demo.getCarInsuranceName(person);

        // 1. 采用防御式检查减少NullPointerException

        // 第一种尝试：深层质疑
        demo.getCarInsuranceName1(person);

        // 第二种尝试：过多的退出语句
        demo.getCarInsuranceName2(person);

        // 2. null带来的种种问题

        // - 它是错误之源
        // - 它会使你的代码膨胀
        // - 它自身是毫无意义的
        // - 它破坏了Java哲学
        // - 它在Java的类型系统上开了个口子

        /************** 10.2 Optional类入门 **************/
        System.out.println("/************** 10.2 Optional类入门 **************/");
        System.out.println();

        // 使用Optional<T>意味着，如果你知道一个人可能有也可能没有车，那么Person类内部的car变量就不应该声明为Car
        // 而是应该直接将其声明为Optional<Car>类型
        // 变量存在时，Optional类只是对类简单封装。变量不存在时，缺失的值会被建模成一个空的Optional对象，由方法
        // Optional.empty()返回。

        // Optional.empty()方法是一个静态工厂方法，它返回Optional类的特定单一实例。

        // 使用Optional重新定义Person/Car/Insurance数据模型
        /**
         * {@link OptionalPerson}
         * {@link OptionalCar}
         * {@link OptionalInsurance}
         */

        // 在你的代码里，应该始终如一的使用Optional，能非常清晰的界定出变量值的缺失是结构上的问题，还是你算法上的缺陷，
        // 还是你数据中的问题。

        /************** 10.3 应用Optional的几种模式 **************/
        System.out.println("/************** 10.3 应用Optional的几种模式 **************/");
        System.out.println();

        // 1. 创建Optional对象
        // 1.1 声明一个空的Optional
        Optional<Car> optCar = Optional.empty();
        // 1.2 依据一个非空值创建Optional  如果car是一个null，这段代码会立即抛出一个NullPointerException
        Optional<Car> optCar2 = Optional.of(person.getCar());
        // 1.3 可接受null的Optional  如果car是null，那么得到的Optional对象就是个空对象
        Optional<Car> optCar3 = Optional.ofNullable(person.getCar());

        // 2. 使用map从Optional对象中提取和转换值
        // 从对象中提取信息是一种比较常见的模式，Optional提供了一个map方法。它的工作方式如下：
        Optional<Insurance> optInsurance = Optional.ofNullable(person.getCar().getInsurance());
        Optional<String> name = optInsurance.map(Insurance::getName);
        // 如果Optional包含一个值，那函数就将该值作为参数传递给map，对该值进行转换。如果Optional为空，就什么也不做。

        // 3. 使用flatMap链接Optional对象

        // 举例说明：传递给optional的flatMap方法的函数会将原始包含正方形的optional对象转换为包含三角形的optional
        // 对象。如果将该方法传递给map方法，结果会是一个Optional对象，而这个Optional对象中包含了三角形，但flatMap
        // 方法会将这种两层的Optional对象转换为包含三角形的单一Optional对象。

        // 3.1 使用Optional获取car的保险公司名称
        Optional<OptionalPerson> optPerson = Optional.of(new OptionalPerson());
        demo.getCarInsuranceName3(optPerson);

        //*******************************
        // 你永远不应该忘记语言的首要功能就是沟通，即使对程序设计语言而言也没什么不同。声明方法接受一个
        // Optional参数，或者将结果作为Optional类型返回，让你的同事或者未来你方法的使用者，很清楚的知道
        // 它可以接受空值，或者它可能返回一个空值。
        //*******************************

        // 3.2 使用Optional解引用串接的Person/Car/Insurance对象

        /**
         * 由于Optional类设计时就没特别考虑将其作为类的字段使用，所以它也并未实现Serializable接口。
         * 由于这个原因，如果你的应用使用了某些要求序列化的库或者框架，在域模型中使用Optional，有可能
         * 引发程序故障。
         *
         * 然而，你已经看到用Optional声明域模型中的某些类型是个不错的主意，尤其是你需要遍历有可能全部
         * 或者部分为空，或者可能不存在的对象时。如果你一定要实现序列化的域模型，作为替代方案，我们建议
         * 你像下面这个例子一样，提供一个能访问声明为Optional、变量值可能缺失的接口，代码清单如下：
         * public class Person {
         *     private Car car;
         *     public Optional<Car> getCarAsOptional() {
         *          return Optional.ofNullable(car);
         *     }
         * }
         */

        // 3.4 默认行为及解引用Optional对象
        // Optional类提供了多种方法读取Optional实例中的变量值：
        // - get() 最简单又最不安全的方法。如果变量不存在，抛出一个NoSuchElementException异常。
        // - orElse(T other) 它允许你在Optional对象不包含值时提供一个默认值。
        // - orElseGet(Supplier<? extends T> other)，是orElse方法的延迟调用版，Supplier方法只有
        //   在Optional对象不含值时才执行调用。
        // - orElseThrow(Supplier<? extend X> exceptionSupplier)，和get方法非常相似，在Optional对象为空时
        //   都会抛出一个异常。
        // - ifPresent(Consumer<? super T>)，让你能在变量值存在时执行一个作为参数传入的方法。

        // 3.5 两个Optional对象的组合

        // 假设有这样一个方法，它接受一个Person和一个Car对象，并以此为条件对外部提供服务进行查询，通过一些复杂的业务
        // 逻辑，试图找到满足该组合的最便宜的保险公司：
        Optional<OptionalCar> optionalCar = Optional.of(new OptionalCar());
        demo.nullSafeFindCheapestInsurance(optPerson, optionalCar);

        // 3.6 使用filter剔除特定的值

        // 假设在我们的Person/Car/Insurance模型中，Person还提供了一个方法可以取得Person对象的年龄，
        // 找出年龄大于或等于minAge参数的Person所对应的保险公司列表：
        demo.getCarInsuranceName4(optPerson, 4);

        // | 方法 | 描述 |
        // | :--- | :--- |
        // | empty | 返回一个空的Optional对象 |
        // | filter | 如果值存在并且满足提供的谓词，就返回包含该值的Optional对象，否则返回一个空的Optional对象 |
        // | filterMap | 如果值存在，就对该值执行提供的mapping函数调用，返回一个Optional类型的值 |
        // | get | 如果该值不存在，抛出一个NoSuchElementException异常 |
        // | ifPresent | 如果值存在，就执行使用该值的方法调用，否则什么也不做 |
        // | isPresent | 如果值存在就返回true，否则返回false |
        // | map | 如果值存在，就对该值执行提供的mapping函数调用 |
        // | of | 将该值用Optional封装后返回，如果该值为null，抛出一个NullPointerException异常 |
        // | ofNullable | 将指定值用Optional封装之后返回，如果该值为null，则返回一个空的Optional对象 |
        // | orElse | 如果有值则将其返回，否则返回一个默认值 |
        // | orElseGet | 如果有值将其返回，否则返回一个由指定的Supplier接口生成的值 |
        // | orElseThrow | 如果有值则返回，否则抛出一个由指定的Supplier接口生成的异常 |

        /************** 10.4 使用Optional的实战示例 **************/
        System.out.println("/************** 10.4 使用Optional的实战示例 **************/");
        System.out.println();

        // 1. 用Optional封装可能为null的值

        // 假设你有一个map
        Map<String, Object> map = new HashMap<>();
        // 如果map中没有与key关联的值，下面的调用就会返回一个null
        Object value = map.get("key");
        // 使用Optional封装map的返回值，可以对这段代码进行优化：
        Optional<Object> value2 = Optional.ofNullable(map.get("key"));

        // 2. 异常与Optional的对比

        // 使用Integer.parseInt(String)，将String转换为int。如果String无法解析到对应的整数，就会抛出一个NumberFormatException异常
        // 我们可以用空的Optional对象，对遭遇无法转换的String时返回的非法值进行建模：
        stringToInt("123");

        // 基础类型的Optional对象，以及为什么应该避免使用它们
        // Optional也提供了类似的基础类型，OptionalInt、OptionalLong、OptionalDouble
        // 不推荐使用基础类型的Optional的原因是，基础类型的Optional不支持map、flatMap以及filter方法

        // 3. 把所有内容整合起来
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");

        // 假设我们的程序需要从这些属性中读取一个值，如果给定的属性值是一个代表正整数的字符串，返回该整数值。
        // 任何其他情况都返回0.

        // 以命令式编程的方式从属性中读取duration值
        demo.readDuration(props, "a");
        // 使用Optional从属性中读取duration
        demo.readDuration(props, "a");


    }

    public String getCarInsuranceName(Person person) {
        return person.getCar().getInsurance().getName();
    }

    public String getCarInsuranceName1(Person person) {
        if (person != null) {
            Car car = person.getCar();
            if (car != null) {
                Insurance insurance = car.getInsurance();
                if (insurance != null) {
                    return insurance.getName();
                }
            }
        }

        return "Unknown";
    }

    public String getCarInsuranceName2(Person person) {
        if (person == null) {
            return "Unknown";
        }
        Car car = person.getCar();
        if (car == null) {
            return "Unknown";
        }
        Insurance insurance = car.getInsurance();
        if (insurance == null) {
            return "Unknown";

        }
        return insurance.getName();
    }

    public String getCarInsuranceName3(Optional<OptionalPerson> person) {
        return person.flatMap(OptionalPerson::getCar)
                .flatMap(OptionalCar::getInsurance)
                .map(OptionalInsurance::getName)
                .orElse("Unknown");
    }

    public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<OptionalPerson> person,
                                                             Optional<OptionalCar> car) {
        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p ,c)));
    }

    public Insurance findCheapestInsurance(OptionalPerson person, OptionalCar car) {
        Insurance cheapestCompany = new Insurance();
        return cheapestCompany;
    }

    public String getCarInsuranceName4(Optional<OptionalPerson> person, int minAge) {
        return person.filter(p -> p.getAge() >= minAge)
                .flatMap(OptionalPerson::getCar)
                .flatMap(OptionalCar::getInsurance)
                .map(OptionalInsurance::getName)
                .orElse("Unknown");
    }

    public static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public int readDuration(Properties props, String name) {
        String value = props.getProperty(name);
        if (value != null) {
            try {
                int i = Integer.parseInt(value);
                if (i > 0) {
                    return i;
                }
            } catch (NumberFormatException e) {

            }
        }

        return 0;
    }

    public int readDuration2(Properties props, String name) {
        return Optional.ofNullable(props.getProperty(name))
                .flatMap(OptionalDemo::stringToInt)
                .filter(i -> i > 0)
                .orElse(0);
    }
}
