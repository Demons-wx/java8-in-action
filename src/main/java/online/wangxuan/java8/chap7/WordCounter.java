package online.wangxuan.java8.chap7;

/**
 * 在归约流时，你得保留由两个变量组成的状态：一个int用来计算到目前为止数过的字数，还有一个boolean用来记得
 * 上一个遇到的Character是不是空格。因为Java没有tuple，所以你必须创建一个新类WordCounter来把这个状态
 * 封装起来。
 * @author wangxuan
 * @date 2018/12/9 1:07 PM
 */

public class WordCounter {

    private final int counter;
    private final boolean lastSpace;
    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    // 和迭代算法一样，accumulate方法一个个遍历Character
    public WordCounter accumulate(Character c) {
        if (Character.isWhitespace(c)) {
            return lastSpace ? this : new WordCounter(counter, true);
        } else {
            // 上一次字符是空格，而当前遍历的字符不是空格时，将单词计数器加一
            return lastSpace ? new WordCounter(counter + 1, false) : this;
        }
    }

    public WordCounter combine(WordCounter wordCounter) {
        return new WordCounter(counter + wordCounter.counter,
                 wordCounter.lastSpace);
    }

    public int getCounter() {
        return counter;
    }
}
