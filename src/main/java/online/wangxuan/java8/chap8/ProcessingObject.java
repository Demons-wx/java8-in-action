package online.wangxuan.java8.chap8;

/**
 * 通常，这种模式是通过定义一个代表处理对象的抽象类来实现的，在抽象类中会定义一个字段来记录后续对象。
 * 一旦对象完成它的工作，处理对象就会将它的工作转交给它的后继。
 * @author wangxuan
 * @date 2019/1/6 10:29 PM
 */

public abstract class ProcessingObject<T> {

    protected ProcessingObject<T> successor;
    public void setSuccessor(ProcessingObject<T> successor) {
        this.successor = successor;
    }
    public T handle(T input) {
        T r = handleWork(input);
        if (successor != null) {
            return successor.handle(r);
        }
        return r;
    }

    abstract protected T handleWork(T input);
}

/**
 * 下面我们看看如何使用该设计模式，你可以创建两个处理对象，它们的功能是进行一些文本处理工作：
 */
class HandleTextProcessing extends ProcessingObject<String> {
    @Override
    protected String handleWork(String input) {
        return "From Raoul, Mario and Alan: " + input;
    }
}

class SpellCheckerProcessing extends ProcessingObject<String> {
    @Override
    protected String handleWork(String input) {
        return input.replaceAll("labda", "lambda");
    }
}


