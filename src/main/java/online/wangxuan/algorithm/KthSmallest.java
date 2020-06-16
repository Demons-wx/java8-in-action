package online.wangxuan.algorithm;

/**
 * @author wangxuan
 * @date 2020/3/28 4:51 PM
 */

public class KthSmallest {

    public static int kthSmallest(int[] arr, int k) {
        if (arr == null || arr.length < k) {
            return -1;
        }
        int n = arr.length - 1;
        int p = partition(arr, 0, n);
        while (k != p + 1) {
            if (k > p + 1) {
                p = partition(arr, p + 1, n);
            } else {
                p = partition(arr, 0, p - 1);
            }
        }

        return arr[p];
    }

    private static int partition(int[] arr, int start, int end) {
        int pivot = arr[end];
        int i = start;
        for (int j = start; j < end; j++) {
            // 这里要是 <= ，不然会出现死循环，比如查找数组 [1,1,2] 的第二小的元素
            // 因为外层的循环终止条件不同，快排只需要保证分区元素左右两侧有序即可。
            // 此处则是根据返回的分区元素判断终止，所以返回不能循环
            if (arr[j] <= pivot) {
                swap(arr, i++, j);
            }
        }
        swap(arr, i, end);
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] a = {1, 1, 2};
        System.out.println(kthSmallest(a, 2));
    }
}
