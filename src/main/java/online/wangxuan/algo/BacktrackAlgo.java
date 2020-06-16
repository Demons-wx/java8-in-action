package online.wangxuan.algo;

/**
 * @author xwangr
 * @date 2020/4/16
 */
public class BacktrackAlgo {

    private static int minDist = Integer.MAX_VALUE;
    private static int[] path;

    /**
     * 数据结构与算法之美 41 动态规划理论
     * 调用方式：minDistBT(0, 0, 0, w, n);
     * @param i 矩阵纵向位置
     * @param j 矩阵横向位置
     * @param dist 距离总和
     * @param w 矩阵
     * @param n n*n矩阵
     */
    public static void minDistBT(int i, int j, int dist, int[][] w, int n) {
        dist += w[i][j];
        path[i + j] = w[i][j];
        if (i == n - 1 && j == n - 1) {
            if (dist < minDist) {
                minDist = dist;
            }
//            printPath();
            return;
        }

        if (i < n - 1) { // 往下走，更新i=i+1, j=j
            minDistBT(i + 1, j, dist, w, n);
        }

        if (j < n - 1) { // 往右走，更新i=i, j=j+1
            minDistBT(i, j + 1, dist, w, n);
        }
    }

    private static void printPath() {
        int sum = 0;
        for (int k = 0; k < path.length; k++) {
            sum += path[k];
            if (k == path.length - 1) System.out.print(path[k]);
            else System.out.print(path[k] + "->");
        }
        System.out.println(" sum = " + sum);
    }



    public static void main(String[] args) {
        int[][] w = {{1, 3, 5, 9},
                     {2, 1, 3, 4},
                     {5, 2, 6, 7},
                     {6, 8, 4, 3}};
        path = new int[2 * w.length - 1];
        minDistBT(0, 0, 0, w, w.length);
//        System.out.println("minDist = " + minDist);
    }
}
