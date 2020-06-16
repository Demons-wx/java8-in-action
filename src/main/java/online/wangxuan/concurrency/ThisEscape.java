package online.wangxuan.concurrency;

/**
 * @author wangxuan
 * @date 2020/3/24 11:12 PM
 */

public class ThisEscape {

    private final Integer number;

    public ThisEscape() {
        new Thread(new EscapeRunnable()).start();
        number = 2;
    }

    public void doSomething() {
        System.out.println(Thread.currentThread().getName() + ": i'm number " + number);
    }

    private class EscapeRunnable implements Runnable {

        @Override
        public void run() {
            doSomething();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new ThisEscape();
        }
    }
}
