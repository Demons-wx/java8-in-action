package online.wangxuan.java8.chap3;

import java.util.function.Function;

/**
 * 函数复合的作用
 *
 * 假设你有一系列工具方法，对用String表示的一封信做文本转换
 * @author wangxuan
 * @date 2018/10/29 10:27 PM
 */

public class FunctionComplex {

    static class Letter {

        public static String addHeader(String text) {
            return "From Raoul, Mario and Alan: " + text;
        }

        public static String addFooter(String text) {
            return text + " Kind regards";
        }

        public static String checkSpelling(String text) {
            return text.replaceAll("labda", "lambda");
        }
    }

    public static void main(String[] args) {
        // 创建一个流水线，先加上抬头，然后再进行拼写检查，再加上落款
        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> transformationPipeline
                = addHeader.andThen(Letter::checkSpelling)
                .andThen(Letter::addFooter);


    }
}
