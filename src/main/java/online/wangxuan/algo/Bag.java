package online.wangxuan.algo;

/**
 * 0-1背包问题
 * @author xwangr
 * @date 2020/4/14
 */
public class Bag {

    // 存储背包中物品总重量的最大值
    public int maxW = Integer.MIN_VALUE;

    /**
     *
     * 流程大概就是：
     * 第一个不放，第二个不放，……，第n-1个不放，第n个不放。
     * 第一个不放，第二个不放，……，第n-1个不放，第n个放。
     * 第一个不放，第二个不放，……，第n-1个放，第n个不放。
     * 第一个不放，第二个不放，……，第n-1个放，第n个放。
     * ……
     * 以此类推
     *
     * 回溯法
     * @param i 表示考察到哪个物品
     * @param cw 表示当前已经装进去物品的重量和
     * @param items 表示每个物品的重量
     * @param n 表示物品个数
     * @param w 背包重量
     */
    public void f(int i, int cw, int[] items, int n, int w) {
        // 装满了或者已考察完所有物品
        if (cw == w || i == n) {
            if (cw > maxW) {
                maxW = cw;
            }
            return;
        }

        // 当前物品不装进背包
        f(i + 1, cw, items, n, w);
        // 已经超过背包可以承受的重量时，停止
        if (cw + items[i] <= w) {
            // 当前物品装进背包
            f(i + 1, cw + items[i], items, n, w);
        }
    }

    public static void main(String[] args) {
        Bag bag = new Bag();
        int[] items = {1, 2, 3};
        bag.f(0, 0, items, items.length, 4);
    }
}
