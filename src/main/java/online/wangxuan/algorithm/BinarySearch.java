package online.wangxuan.algorithm;

/**
 * @author wangxuan
 * @date 2020/4/5 9:53 AM
 */

public class BinarySearch {

    /**
     * 二分查找
     * @param a 数组
     * @param n 数组长度
     * @param k 待查找元素
     * @return 位置
     */
    public static int bSearch(int[] a, int n, int k) {
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (k == a[mid]) return mid;
            if (k > a[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 二分查找 递归实现
     * @param a 数组
     * @param n 数组长度
     * @param k 待查找元素
     * @return 位置
     */
    public static int bSearchRecursive(int[] a, int n, int k) {
        return bSearchInner(a, 0, n - 1, k);
    }

    private static int bSearchInner(int[] a, int low, int high, int k) {
        if (low > high) return -1;
        int mid = low + (high - low) / 2;
        if (a[mid] == k) return mid;
        if (a[mid] < k) {
            return bSearchInner(a, mid + 1, high, k);
        } else {
            return bSearchInner(a, low, mid - 1, k);
        }
    }

    /**
     * 查找第一个值等于给定值的元素
     * @param a 数组
     * @param n 数组长度
     * @param k 待查找元素
     * @return 位置
     */
    public static int bSearch1(int[] a, int n, int k) {
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (k == a[mid]) {
                if (mid == 0 || a[mid - 1] != k) return mid;
                high = mid - 1;
            } else if (k > a[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个值等于给定值的元素
     * @param a 数组
     * @param n 数组长度
     * @param k 待查找元素
     * @return 位置
     */
    public static int bSearch2(int[] a, int n, int k) {
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (k == a[mid]) {
                if (mid == n - 1 || a[mid + 1] != k) return mid;
                low = mid + 1;
            } else if (k > a[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 查找第一个大于等于给定值的元素
     * @param a 数组
     * @param n 数组长度
     * @param k 待查找元素
     * @return 位置
     */
    public static int bSearch3(int[] a, int n, int k) {
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (a[mid] >= k) {
                if (mid == 0 || a[mid -1] < k) return mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个小于等于给定值的元素
     * @param a 数组
     * @param n 数组长度
     * @param k 待查找元素
     * @return 位置
     */
    public static int bSearch4(int[] a, int n, int k) {
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (a[mid] <= k) {
                if (mid == n - 1 || a[mid + 1] > k) return mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
