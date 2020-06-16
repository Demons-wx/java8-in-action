package online.wangxuan.algo;

/**
 * @author xwangr
 * @date 2020/3/31
 */
public class BinarySearch {

    // 二分查找(循环)，在数组中没有重复元素时适用
    public static int bSearch(int[] a, int n, int value) {
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (a[mid] == value) return mid;

            if (a[mid] > value) {
                high = mid - 1;
            }

            if (a[mid] < value) {
                low = mid + 1;
            }
        }
        return -1;
    }

    // 二分查找(递归)，在数组中没有重复元素时适用
    public static int bSearchRecursive(int[] a, int n, int value) {
        return bSearchInternal(a, 0, n - 1, value);
    }

    private static int bSearchInternal(int[] a, int low, int high, int value) {
        if (low > high) return -1;
        int mid = low + (high - low) / 2;
        if (a[mid] == value) return mid;

        if (a[mid] > value) {
            return bSearchInternal(a, low, mid - 1, value);
        } else {
            return bSearchInternal(a, mid + 1, high, value);
        }
    }

    // 查找给定值第一次出现的位置
    public static int bSearch1(int[] a, int n, int value) {
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (a[mid] == value) {
                if (mid == 0 || a[mid - 1] != value) return mid;
                high = mid - 1;
            } else if (a[mid] > value) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    // 查找给定值最后一次出现的位置
    public static int bSearch2(int[] a, int n, int value) {
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (a[mid] == value) {
                if (mid == n - 1 || a[mid + 1] != value) return mid;
                low = mid + 1;
            } else if (a[mid] > value){
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    // 查找第一个大于给定值的元素
    public static int bSearch3(int[] a, int n, int value) {
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (a[mid] <= value) {
                low = mid + 1;
            } else {
                if (mid == 0 || a[mid - 1] <= value) return mid;
                high = mid - 1;
            }
        }
        return -1;
    }

    // 查找最后一个小于给定值的元素
    public static int bSearch4(int[] a, int n, int value) {
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (a[mid] >= value) {
                high = mid - 1;
            } else {
                if (mid == n - 1 || a[mid + 1] >= value) return mid;
                low = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 6, 6, 6, 12, 13,15};
        System.out.println(bSearchRecursive(a, a.length, 6));
        System.out.println(bSearch1(a, a.length, 6));
        System.out.println(bSearch2(a, a.length, 6));
        System.out.println(bSearch3(a, a.length, 6));
        System.out.println(bSearch4(a, a.length, 6));
    }
}
