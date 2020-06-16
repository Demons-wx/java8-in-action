package online.wangxuan.algo;

/**
 * @author xwangr
 * @date 2020/3/26
 */
public class Sorts {

    public static void bubble(int[] a, int n) {
        if (n <= 1) return;
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) break;
        }
    }

    public static void insert(int[] a, int n) {
        for (int i = 1; i < n; i++) {
            int target = a[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (a[j] > target) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            a[j + 1] = target;
        }
    }

    public static void selection(int[] a, int n) {
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = temp;
        }
    }

    public static void mergeSort(int[] a, int n) {
        merging(a, 0, n - 1);
    }

    private static void merging(int[] array, int start, int end) {
        if (start >= end) return;
        int middle = (start + end) / 2;
        merging(array, start, middle);
        merging(array, middle + 1, end);
        merge(array, start, middle, end);
    }

    private static void merge(int[] array, int start, int middle, int end) {
        int i = start, j = middle + 1, k = 0;
        int[] temp = new int[end - start + 1];
        while (i <= middle && j <= end) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i <= middle) {
            temp[k++] = array[i++];
        }

        while (j <= end) {
            temp[k++] = array[j++];
        }

        int s = 0;
        while (s < k) {
            array[start++] = temp[s++];
        }
    }

    public static void quickSort(int[] a, int n) {
        quickInternal(a, 0, n - 1);
    }

    private static void quickInternal(int[] a, int start, int end) {
        if (start >= end) return;
        int p = partition(a, start, end);
        quickInternal(a, start, p - 1);
        quickInternal(a, p + 1, end);
    }

    private static int partition(int[] a, int start, int end) {
        int compare = a[end];
        int i = start;
        for (int j = start; j < end; j++) {
            if (a[j] < compare) {
                swap(a, i++, j);
            }
        }
        swap(a, i, end);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    /**
     *
     * @param a
     * @param bucketSize 桶容量
     */
    public static void bucketSort(int[] a, int bucketSize) {
        if (a.length < 2) return;
        // 数组最小最大值
        int min = a[0], max = a[1];
        for (int i = 0; i < a.length; i++) {
            if (a[i] < min) {
                min = a[i];
            }
            if (a[i] > max) {
                max = a[i];
            }
        }

        // 桶数量
        int bucketCount = (max - min) / bucketSize + 1;
        int[][] buckets = new int[bucketCount][bucketSize];
        // 保存每个桶里元素的数量
        int[] indexArr = new int[bucketCount];

        // 将数组中的值分配到各个桶里
        for (int i = 0; i < a.length; i++) {
            int bucketIndex = (a[i] - min) / bucketSize;
            if (indexArr[bucketIndex] == buckets[bucketIndex].length) {
                ensureCapacity(buckets, bucketIndex);
            }
            buckets[bucketIndex][indexArr[bucketIndex]++] = a[i];
        }

        // 对每个桶进行排序，快排
        int k = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (indexArr[i] == 0) continue;
            quickSort(buckets[i], indexArr[i]);
            for (int j = 0; j < indexArr[i]; j++) {
                a[k++] = buckets[i][j];
            }
        }
    }

    private static void ensureCapacity(int[][] buckets, int bucketIndex) {
        int[] tmpArr = buckets[bucketIndex];
        int[] newArr = new int[tmpArr.length * 2];
        for (int i = 0; i < tmpArr.length; i++) {
            newArr[i] = tmpArr[i];
        }
        buckets[bucketIndex] = newArr;
    }

    public static void prettyPrint(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
        }
    }



    public static void main(String[] args) {
        int[] a = {4, 5, 6, 1, 2, 3, 3, 1};
//        mergeSort(a, a.length);
//        quickSort(a, a.length);
//        bucketSort(a, 5);
        prettyPrint(a);
    }
}
