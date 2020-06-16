package online.wangxuan.algo;

/**
 * 编辑距离
 *
 * @author xwangr
 * @date 2020/4/21
 */
public class EditDistance {

    private static char[] a = "liudehua".toCharArray();
    private static char[] b = "liudefa".toCharArray();
    private static int n = a.length;
    private static int m = b.length;
    private static int minDist = Integer.MAX_VALUE;

    /**
     * 莱文斯坦距离（Levenshtein distance）回溯法
     * <p>
     * 调用方式：lwstBT(0, 0, 0);
     *
     * @param i     字符串a下标
     * @param j     字符串b下标
     * @param edist 表示处理到a[i]和b[j]时，已经执行的编辑操作次数
     */
    public void lwstBT(int i, int j, int edist) {
        if (i == n || j == m) {
            if (i < n) edist += n - i;
            if (j < m) edist += m - j;
            if (edist < minDist) minDist = edist;
            return;
        }

        if (a[i] == b[j]) { // 两个字符匹配
            lwstBT(i + 1, j + 1, edist);
        } else {
            lwstBT(i + 1, j, edist + 1); // 删除a[i] 或者 在b[j]前添加一个跟a[i]相同的字符
            lwstBT(i, j + 1, edist + 1); // 删除b[j] 或者 在a[i]前添加一个跟b[j]相同的字符
            lwstBT(i + 1, j + 1, edist + 1); // 将a[i]和b[j]替换为相同字符
        }
    }

    /**
     * 莱文斯坦距离（Levenshtein distance）动态规划
     *
     * 状态 (i, j) 可能从 (i-1, j)，(i, j-1)，(i-1, j-1) 三个状态中的任意一个转移过来
     *
     * 状态转移方程：
     * - 如果a[i] != b[j] 那么minEdist(i,j) = min(minEdist(i-1, j) + 1, minEdist(i, j-1) + 1, minEdist(i-1, j-1) + 1)
     * - 如果a[i] == b[j] 那么minEdist(i,j) = min(minEdist(i-1, j) + 1, minEdist(i, j-1) + 1, minEdist(i-1, j-1))
     *
     * 状态转移表：
     *     m   t   a   c   n   u
     * m   0   1   2   3   4   5
     * i   1   1   2   3   4   5
     * t   2   1   2   3   4   5
     * c   3   2   2   2   3   4
     * m   4   3   3   3   3   4
     * u   5   4   4   4   4   3
     *
     * @param a 字符串a
     * @param n 字符串a长度
     * @param b 字符串b
     * @param m 字符串b长度
     * @return  编辑距离
     */
    public int lwstDP(char[] a, int n, char[] b, int m) {
        int[][] minDist = new int[n][m];

        // 初始化第0行
        for (int i = 0; i < m; i++) {
            if (a[0] == b[i]) minDist[0][i] = i;
            else if (i != 0) minDist[0][i] = minDist[0][i - 1] + 1;
            else minDist[0][i] = 1;
        }

        // 初始化第0列
        for (int i = 0; i < n; i++) {
            if (a[i] == b[0]) minDist[i][0] = i;
            else if (i != 0) minDist[i][0] = minDist[i - 1][0] + 1;
            else minDist[i][0] = 1;
        }

        for (int i = 1; i < n; i++) {   // 按行填写
            for (int j = 1; j < m; j++) {
                if (a[i] == b[j]) {
                    minDist[i][j] = min(minDist[i - 1][j] + 1, minDist[i][j - 1] + 1, minDist[i - 1][j - 1]);
                } else {
                    minDist[i][j] = min(minDist[i - 1][j] + 1, minDist[i][j - 1] + 1, minDist[i - 1][j - 1] + 1);
                }
            }
        }
        return minDist[n - 1][m - 1];
    }

    private int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static void main(String[] args) {
        EditDistance ed = new EditDistance();
//        ed.lwstBT(0, 0, 0);
//        System.out.println(ed.minDist);
        System.out.println(ed.lwstDP(a, n, b, m));

    }
}
