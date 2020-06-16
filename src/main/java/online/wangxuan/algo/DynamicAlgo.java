package online.wangxuan.algo;

/**
 * @author xwangr
 * @date 2020/4/15
 */
public class DynamicAlgo {

    /**
     * 这就是一种用动态规划解决问题的思路。
     * 我们把问题分解为多个阶段，每个阶段对应一个决策。
     * 我们记录每一个阶段可达的状态集合（去掉重复的），然后通过当前阶段的状态集合，
     * 来推导下一个阶段的状态集合，动态地往前推进。
     * 这也是动态规划这个名字的由来。
     *
     *
     * 动态规划解背包问题
     * @param weight 物品重量 {2, 2, 4, 6, 3}
     * @param n 物品个数 5
     * @param w 背包可承载重量 9
     * @return 装后的背包重量
     */
    public int knapsack(int[] weight, int n, int w) {
        // 记录每层可以达到的状态，states[0][0], states[0][2]表示第0层能达到的状态
        boolean[][] states = new boolean[n][w+1];
        states[0][0] = true;
        if (weight[0] <= w) {
            states[0][weight[0]] = true;
        }

        // 动态规划状态转移
        for (int i = 1; i < n; i++) {
            // 不把第i个物品放入背包
            for (int j = 0; j <= w; j++) {
                if (states[i-1][j]) {
                    states[i][j] = states[i-1][j];
                }
            }

            // 把第i个物品放入背包
            for (int j = 0; j <= w - weight[i]; j++) {
                if (states[i-1][j]) {
                    states[i][j + weight[i]] = true;
                }
            }
        }

        // 输出结果
        for (int i = w; i >= 0; i--) {
            if (states[n-1][i]) {
                return i;
            }
        }

        return 0;
    }

    /**
     * 数据结构与算法之美 41 动态规划理论
     * 求矩阵[0,0] - [n-1, n-1]的最短路径 (状态转移表法)
     * @param matrix 矩阵
     * @param n  n*n矩阵
     * @return 最短路径长度
     */
    public int minDistDP(int[][] matrix, int n) {
        // 用来记录到达某一位置的最短路径
        int[][] states = new int[n][n];
        int sum = 0;
        for (int i = 0; i < n; i++) { // 初始化states的第一行数据
            sum += matrix[0][i];
            states[0][i] = sum;
        }

        sum = 0;
        for (int i = 0; i < n; i++) { // 初始化states的第一列数据
            sum += matrix[i][0];
            states[i][0] = sum;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                states[i][j] = matrix[i][j] + Math.min(states[i-1][j], states[i][j-1]);
            }
        }
        return states[n-1][n-1];
    }

    private int[][] matrix = {{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}};
    private int n = 4;
    // mem记录到达某一位置的最短路径
    private int[][] mem = new int[4][4];
    /**
     * 数据结构与算法之美 41 动态规划理论
     * 求矩阵[0,0] - [n-1, n-1]的最短路径 (状态转移方程法)
     * 状态转移方程：min_dist(i, j) = w[i][j] + min(min_dist(i, j-1), min_dist(i-1, j))
     *
     * 动态规划的三个特征：
     * - 最优子结构，问题的最优解包含子问题的最优解
     * - 无后效性，我们只关心当前阶段的状态值，不关心它的推导过程。后面的决策不影响当前阶段的状态
     * - 重复子问题，不同的决策序列，到某个相同的阶段，可能产生重复状态
     * @param i 初始n-1
     * @param j 初始n-1
     * @return 最短路径长度
     */
    public int minDist(int i, int j) {
        if (i == 0 && j == 0) return matrix[0][0];
        if (mem[i][j] > 0) return mem[i][j];

        int minLeft = Integer.MAX_VALUE;
        if (j - 1 >= 0) {
            minLeft = minDist(i, j - 1);
        }

        int minUp = Integer.MAX_VALUE;
        if (i - 1 >= 0) {
            minUp = minDist(i - 1, j);
        }

        int currMinDist = matrix[i][j] + Math.min(minLeft, minUp);
        mem[i][j] = currMinDist;
        return currMinDist;
    }


    private int maxV = Integer.MAX_VALUE;   // 结果放再maxV中
    private int[] items = {2, 2, 4, 6, 3};  // 物品重量
    private int[] value = {3, 4, 8, 9, 6};  // 物品价值
    private int num = 5;                    // 物品个数
    private int weight = 9;                 // 背包承受最大重量

    /**
     * 背包升级版 (回溯法)
     *
     * 我们现在引入物品价值这一变量。对于一组不同重量、不同价值、不可分割的物品，
     * 我们选择将某些物品装入背包，在满足背包最大重量限制的前提下，
     * 背包中可装入物品的总价值最大是多少呢？
     *
     * @param i     表示考察到哪个物品
     * @param cw    当前背包总重量
     * @param cv    当前背包总价值
     */
    public void knapsackV2(int i, int cw, int cv) {
        if (cw == weight || i == num) {
            if (cv > maxV) maxV = cv;
            return;
        }
        knapsackV2(i + 1, cw, cv);
        if (cw + items[i] <= weight) {
            knapsackV2(i + 1, cw + items[i], cv + value[i]);
        }
    }

    /**
     * 背包升级版 (动态规划)
     *
     * @param weight    物品重量 {2, 2, 4, 6, 3}
     * @param value     物品价值 {3, 4, 8, 9, 6}
     * @param n         物品个数 5
     * @param w         背包可承载重量 9
     * @return          最高价值
     */
    public int knapsackV2Dynamic(int[] weight, int[] value, int n, int w) {
        int[][] states = new int[n][w+1];   // 状态转移表
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < w + 1; j++) {
                states[i][j] = -1;
            }
        }

        states[0][0] = 0;
        if (weight[0] <= w) {
            states[0][weight[0]] = value[0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= w; j++) {  // 不选择第i个物品
                if (states[i-1][j] >= 0) {
                    states[i][j] = states[i-1][j];
                }
            }

            for (int j = 0; j <= w - weight[i]; j++) {  // 选择第i个物品
                if (states[i-1][j] >= 0) {
                    int v = states[i-1][j] + value[i];
                    if (v > states[i][j + weight[i]]) {
                        states[i][j + weight[i]] = v;
                    }
                }
            }
        }

        int maxValue = -1;
        for (int i = 0; i <= w; i++) {
            if (states[n-1][i] > maxValue) maxValue = states[n-1][i];
        }

        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states[i].length; j++) {
                System.out.print(states[i][j] + "   ");
            }
            System.out.println();
        }

        return maxValue;
    }

    public static void main(String[] args) {
        DynamicAlgo dynamic = new DynamicAlgo();
//        int[][] w = {{1, 3, 5, 9},
//                     {2, 1, 3, 4},
//                     {5, 2, 6, 7},
//                     {6, 8, 4, 3}};
//        System.out.println(dynamic.minDistDP(w, w.length));
        int[] weight = {2, 2, 4, 6, 3};
        int[] values = {3, 4, 8, 9, 6};
        System.out.println(dynamic.knapsackV2Dynamic(weight, values, 5, 9));
    }
}
