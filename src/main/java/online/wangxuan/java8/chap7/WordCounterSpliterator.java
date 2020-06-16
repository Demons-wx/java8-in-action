package online.wangxuan.java8.chap7;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author wangxuan
 * @date 2018/12/9 9:34 PM
 */

public class WordCounterSpliterator implements Spliterator<Character> {

    private final String string;
    private int currentChar = 0;

    public WordCounterSpliterator(String string) {
        this.string = string;
    }

    /**
     * 把String中当前位置的Character传给了Consumer，并让位置加一。
     * 作为参数传递的Consumer是一个Java内部类，在遍历流时将要处理的
     * Character传给了一系列要对其执行的函数。
     * @param action 这是一个归约函数，即WordCounter类的accumulate方法
     * @return 还有要遍历的Character，则返回true
     */
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        // 处理当前字符
        action.accept(string.charAt(currentChar++));
        // 如果还有字符要处理，则返回true
        return currentChar < string.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;
        // 返回null表示要解析的string已经足够小，可以顺序处理
        if (currentSize < 10) {
            return null;
        }
        // 将试探拆分位置设定为要解析的String的中间
        for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
            // 让拆分位置前进至下一个空格
            if (Character.isWhitespace(string.charAt(splitPos))) {
                // 创建一个新WordCounterSpliterator来解析String从开始到拆分位置的部分
                Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
                // 将这个WordCounterSpliterator的起始位置设为拆分位置
                currentChar = splitPos;
                return spliterator;
            }
        }
        return null;
    }

    /**
     * @return String的总长度和当前遍历的位置的差
     */
    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    /**
     * characteristic方法告诉框架这个Spliterator是
     * ORDERED 顺序就是String中各个Character的次序
     * SIZED estimatedSize方法返回值是精确的
     * SUBSIZED trySplit方法创建的其他Spliterator也有确切大小
     * NONNULL String中不能有为null的Character
     * IMMUTABLE 在解析String时不能再添加Character
     * @return
     */
    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
