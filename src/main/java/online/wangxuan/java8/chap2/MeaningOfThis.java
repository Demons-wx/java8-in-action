package online.wangxuan.java8.chap2;

/**
 * @author wangxuan
 * @date 2018/10/27 4:38 PM
 */

public class MeaningOfThis {

    public final int value = 4;
    public void doIt() {
        int value = 6;
        Runnable r = new Runnable() {
            public final int value = 5;
            @Override
            public void run() {
                int value = 10;
                System.out.println(this.value);
            }
        };
        r.run();
    }

    public static void main(String[] args) {
        MeaningOfThis m = new MeaningOfThis();
        m.doIt(); // 5
    }
}
