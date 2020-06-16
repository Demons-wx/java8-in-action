package online.wangxuan.algorithm;

import java.lang.reflect.Method;

/**
 * @author wangxuan
 * @date 2020/3/28 10:15 AM
 */

public class Sorts {

    public static void bubbleSort(int[] a, int n) {
        if (n <= 1) return;
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
    }

    public static void insertSort(int[] a, int n) {
        if (n <= 1) return;
        for (int i = 1; i < n; i++) {
            int curr = a[i];
            int j = i - 1;
            for (; j >= 0; --j) {
                if (curr < a[j]) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            a[j + 1] = curr;
        }
    }

    public static void selectionSort(int[] a, int n) {
        if (n <= 1) return;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }
            int tmp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = tmp;
        }
    }

    public static void mergeSort(int[] a, int n) {
        mergeInternal(a, 0, n - 1);
    }

    private static void mergeInternal(int[] a, int s, int e) {
        if (s >= e) return;
        int m = (s + e) / 2;
        mergeInternal(a, s, m);
        mergeInternal(a, m + 1, e);
        merge(a, s, m, e);
    }

    private static void merge(int[] a, int s, int m, int e) {
        int[] tmp = new int[e - s + 1];
        int k = 0;
        int i = s;
        int j = m + 1;
        while (i <= m && j <= e) {
            if (a[i] <= a[j]) {
                tmp[k++] = a[i++];
            } else {
                tmp[k++] = a[j++];
            }
        }

        while (i <= m) {
            tmp[k++] = a[i++];
        }

        while (j <= e) {
            tmp[k++] = a[j++];
        }

        int n = 0;
        while (n < tmp.length) {
            a[s++] = tmp[n++];
        }
    }

    public static void quickSort(int[] a, int n) {
        quickInternal(a, 0, n - 1);

    }

    private static void quickInternal(int[] a, int s, int e) {
        if (s >= e) return;
        int m = partition(a, s, e);
        quickInternal(a, s, m - 1);
        quickInternal(a, m + 1, e);
    }

    private static int partition(int[] a, int s, int e) {
        int pivot = a[e];
        int i = s;
        for (int j = s; j < e; j++) {
            if (a[j] < pivot) {
                swap(a, i++, j);
            }
        }
        swap(a, i, e);
        return i;
    }

    private static int partition2(int[] a, int s, int e) {
        int pivot = a[s];
        int i = s, j = e + 1;
        while (true) {
            while (a[++i] < pivot) {
                if (i == e) break;
            }
            while (a[--j] > pivot) {
                if (j == s) break;
            }
            if (i >= j) break;
            swap(a, i, j);
        }
        swap(a, j, s);
        return j;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void prettyPrint(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
        }
    }

    public static void main(String[] args) throws Exception {
//        int[] a = {1, 2, 1};
        int[] a = {4, 5, 3, 1, 2, 7, 8, 6};

//        Class<?> clazz = Class.forName("online.wangxuan.algorithm.Sorts");
//        Method bubble = clazz.getMethod("bubbleSort", int[].class, int.class);
//        bubble.invoke(clazz, a, a.length);

        prettyPrint(a);
    }
}
