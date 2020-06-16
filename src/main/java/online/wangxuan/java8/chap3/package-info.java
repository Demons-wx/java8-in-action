/**
 *
 * @author wangxuan
 * @date 2018/10/27 5:24 PM
 */

package online.wangxuan.java8.chap3;

/**
 * 1. 在哪里可以使用Lambda表达式？
 * 你可以在函数式接口上使用Lambda表达式
 *
 * 2. 什么是函数式接口？
 * 函数式接口就是只定义一个抽象方法的接口。如Comparator、Runnable
 *
 * 注意: 接口现在还可以拥有默认方法(即在类没有对方法进行实现时，其主体为方法提供默认实现的方法)。
 * 哪怕有很多默认方法，只要接口只定义了一个抽象方法，它就仍是一个函数式接口。
 *
 * 3. 函数式接口可以做什么？
 * Lambda表达式允许你直接以内联的形式为函数式接口的抽象方法提供实现，并把整个表达式作为函数式接口的实例。
 *
 * 4. @FunctionalInterface
 * 这个注解用于表示该接口会被设计成一个函数式接口。
 *
 * 5. Java8引入的几个新的函数式接口 java.util.function
 * 5.1 Predicate 接口定义了一个名叫test的抽象方法，它接受泛型T对象，并返回一个boolean。
 * 5.2 Consumer 定义了一个名叫accept的抽象方法，它接受泛型T的对象，没有返回。你如果需要访问T类型的对象，并对其执行某些操作，就可以使用这个接口。
 * 5.3 Function 定义了一个叫做apply的方法，它接受一个泛型T的对象，并返回一个泛型R的对象。
 *     下面的代码展示了如何利用它来创建一个map方法，以将一个String列表映射到包含每个String长度的Integer列表：
 *     public interface Function<T,R> {
 *          R apply(T t);
 *     }
 *     public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
 *          List<R> result = new ArrayList<>();
 *          for (T s : list) {
 *              result.add(f.apply(s));
 *          }
 *          return result;
 *     }
 *     List<Integer> l = map(Arrays.asList("lambdas", "in", "action"), (String s) -> s.length());
 *
 * 6. Lambda使用局部变量 (捕获)
 * Lambda表达式引用的局部变量必须是最终的(final)或事实上最终的
 * int portNumber = 1337;
 * Runnable r = () -> System.out.println(portNumber); // 错误
 * portNumber = 31337;
 * 因为, 局部变量保存在栈上，并且隐式表示它们仅限于其所在线程。如果允许捕获可改变的局部变量，就会引发造成线程不安全的
 * 新的可能性，而这是我们不想看到的(实例变量可以，因为它们保存在堆中，而堆在线程之间是共享的)。
 *
 * 7. 方法引用 (Lambda语法糖)
 * 方法引用可以被看作仅仅调用特定方法的Lambda的一种快捷写法。
 * 它的基本思想是，如果一个Lambda代表的只是`直接调用这个方法`，那最好还是用名称来调用它，而不是去描述如何调用它。
 * (Apple a) -> a.getWeight() ==> Apple::getWeight
 *
 * (args) -> ClassName.staticMethod(args) ==> ClassName::staticMethod
 * (arg0, rest) -> arg0.instanceMethod(rest) ==> ClassName::instanceMethod (其中arg0是ClassName类型的)
 * (args) -> expr.instanceMethod(args) ==> expr::instanceMethod
 *
 * 8. 构造函数引用
 * 对于一个现有构造函数，你可以利用它的名称和关键字new来创建一个它的引用: ClassName::new
 *
 */

