package online.wangxuan.jvm;
/**
 * @author wangxuan
 * @date 2020/4/7 10:02 PM
 */

public class BooleanTest {
    static boolean boolValue;
    public static void main(String[] args) {
        boolValue = true; // 将这个 true 替换为 2 或者 3，再看看打印结果
        if (boolValue) System.out.println("Hello, Java!");
        if (boolValue == true) System.out.println("Hello, JVM!");
    }
}
