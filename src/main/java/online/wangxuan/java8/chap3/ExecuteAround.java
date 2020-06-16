package online.wangxuan.java8.chap3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 资源处理时，一个常见的模式就是打开一个资源，做一些处理，然后关闭资源。
 * 这个设置和清理阶段总是很类似，并且会围绕着执行处理的那些重要代码。这就是所谓的环绕执行模式。
 * @author wangxuan
 * @date 2018/10/27 5:43 PM
 */

public class ExecuteAround {

    /**
     * 局限性: 只能读取文件的第一行
     * @return 第一行内容
     * @throws IOException e
     */
    public static String processFileLow() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/wangxuan/IdeaProjects/java8-in-action/src/main/resources/online/wangxuan/chap3/data.txt"))) {
            return br.readLine();
        }
    }

    public static void main(String[] args) throws IOException {

        // 第3步: 传递Lambda
        String oneLine = processFile((BufferedReader br) -> br.readLine());
        String oneLine2 = processFile(BufferedReader::readLine);
        String twoLine = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        System.out.println(twoLine);
    }

    /**
     * 第1步: 使用函数式接口来传递行为
     */
    @FunctionalInterface
    public interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }

    /**
     * 第2步: 执行一个行为
     * @param p BufferedReaderProcessor
     * @return string
     * @throws IOException e
     */
    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/wangxuan/IdeaProjects/java8-in-action/src/main/resources/online/wangxuan/chap3/data.txt"))) {
            return p.process(br);
        }
    }
}
