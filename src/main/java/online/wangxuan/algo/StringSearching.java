package online.wangxuan.algo;

/**
 * @author xwangr
 * @date 2020/4/8
 */
public class StringSearching {

    private final static int[] table = new int[26];
    static {
        int s = 1;
        for (int i = 0; i < 26; i++) {
            table[i] = s;
            s *= 26;
        }
    }

    public static int bF(String source, String dest) {
        int m = source.length(), n = dest.length(), k;
        char[] a = source.toCharArray(), b = dest.toCharArray();
        for (int i = 0; i < m - n; i++) {
            k = 0;
            for (int j = 0; j < n; j++) {
                if (a[i + j] == b[j]) {
                    k++;
                } else {
                    break;
                }
            }

            if (k == n) {
                return i;
            }
        }

        return -1;
    }

    /**
     * RK 算法的思路是这样的：我们通过哈希算法对主串中的 n-m+1 个子串分别求哈希值，然后逐个与模式串的哈希值比较大小。
     * 如果某个子串的哈希值与模式串相等，那就说明对应的子串和模式串匹配了（这里先不考虑哈希冲突的问题，后面我们会讲到）。
     * 因为哈希值是一个数字，数字之间比较是否相等是非常快速的，所以模式串和子串比较的效率就提高了。
     * @param source 源字符串
     * @param dest 模式串
     * @return 起始位置
     */
    public static int rK(String source, String dest) {
        int m = source.length(), n = dest.length(), s;
        char[] a = source.toCharArray(), b = dest.toCharArray();

        int destHash = 0, index;
        for (int i = 0; i < n; i++) {
            index = n - i - 1 >= 26 ? (n - i - 1) % 26 : n - i - 1;
            destHash += (b[i] - 'a') * table[index];
        }

        for (int i = 0; i < m - n; i++) {
            s = 0;
            for (int k = 0; k < n; k++) {
                index = n - k - 1 >= 26 ? (n - k - 1) % 26 : n - k - 1;
                s += (a[i + k] - 'a') * table[index];
            }
            if (s == destHash) {
                return i;
            }
        }
        return -1;
    }

    public static int bm(String source, String dest) {
        int n = source.length(), m = dest.length();
        char[] a = source.toCharArray(), b = dest.toCharArray();

        int[] bc = new int[SIZE];
        generateBc(bc, b, m);

        // i表示主串与模式串对齐的第一个字符
        int i = 0;
        while (i <= n - m) {
            int j;
            // 模式串从后往前匹配
            for (j = m - 1; j >= 0; j--) {
                // 坏字符对应模式串中的下标是j
                if (a[i + j] != b[j]) break;
            }
            // 匹配成功，返回主串与模式串匹配的第一个字符的位置
            if (j < 0) {
                return i;
            }
            // 这里等同于将模式串往后滑动 j - bc[(int) a[i + j]] 位
            // 坏字符对应主串的下标是i + j， 在散列表中找与a[i + j]相同的字符最后出现的位置，然后让它与主串的坏字符对齐
            i = i + (j - bc[(int) a[i + j]]);
        }

        return -1;
    }

    private static final int SIZE = 256;
    /**
     * 模式串中记录字符位置的散列表，如果模式串中存在重复字符，记录最后一次出现的位置
     * @param bc 数组，数组下标为字符的ascii码，值为字符在模式串中的位置
     * @param b 模式串
     * @param m 模式串长度
     */
    private static void generateBc(int[] bc, char[] b, int m) {
        for (int i = 0; i < SIZE; i++) {
            bc[i] = -1;
        }
        for (int i = 0; i < m; i++) {
            int ascii = (int) b[i];
            bc[ascii] = i;
        }
    }

    /**
     *
     * @param b 模式串
     * @param m 模式串长度
     * @param suffix suffix数组下标表示后缀子串的长度，下标对应的值是模式串中跟好后缀相匹配的子串的起始下标值(如果有多个，取最靠后的)
     * @param prefix prefix数组下标也表示后缀子串的长度，下标对应的值是后缀子串是否能匹配模式串的前缀子串
     */
    private static void generateGs(char[] b, int m, int[] suffix, boolean[] prefix) {
        // 初始化
        for (int i = 0; i < m; i++) {
            suffix[i] = -1;
            prefix[i] = false;
        }

        for (int i = 0; i < m - 1; i++) { // b[0, i]
            int j = i;
            // 公共后缀子串长度
            int k = 0;
            // 与b[0, m-1]求公共后缀子串
            while (j >= 0 && b[j] == b[m - 1 - k]) {
                --j;
                ++k;
                // j + 1表示公共后缀子串在b[0,i]中的起始下标
                suffix[k] = j + 1;
            }
            // 如果公共后缀子串也是模式串的前缀子串
            if (j == -1) {
                prefix[k] = true;
            }
        }
    }




    public static void main(String[] args) {
        String source = "abcdefghrewrewrthghfggvdffsdfafewrehrthhnfcscasdasdasdfsgvdfbf", dest = "thghfggvdffsdfafewrehrthhnfcscaadasdasdf";
        System.out.println(bF(source, dest));
        System.out.println(rK(source, dest));
    }
}




