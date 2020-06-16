package online.wangxuan.algorithm;

/**
 * @author wangxuan
 * @date 2020/4/11 10:20 AM
 */

public class MinHeap {

    private int[] a;
    private int n;
    private int count;

    public MinHeap(int capacity) {
        this.a = new int[capacity];
        this.n = capacity;
        this.count = 0;
    }

    public void insert(int value) {
        if (count >= n) return;
        a[++count] = value;
        int i = count;
        while (i / 2 > 0 && a[i / 2] > a[i]) {
            swap(a, i, i / 2);
            i /= 2;
        }

    }

    public void removeMin() {
        if (count == 0) return;
        a[1] = a[count];
        count--;
        heapify(a, 1, count);
    }

    private static void heapify(int[] a, int i, int count) {
        while (true) {
            int minPos = i;
            if (i * 2 <= count && a[i * 2] < a[minPos]) minPos = i * 2;
            if (i * 2 + 1 <= count && a[i * 2 + 1] < a[minPos]) minPos = i * 2 + 1;
            if (minPos == i) return;
            swap(a, i, minPos);
            i = minPos;
        }
    }

    public static void heapSort(int[] a, int n) {
        buildHeap(a, n);
        int k = n;
        while (k > 1) {
            swap(a, 1, k);
            k--;
            heapify(a, 1, k);
        }
    }

    private static void buildHeap(int[] a, int n) {
        for (int i = n / 2; i > 0; i--) {
            heapify(a, i, n);
        }
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void prettyPrint(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            System.out.print(a[i]);
        }
    }

    public static void main(String[] args) {
        int[] a = {0, 3, 5, 6, 9, 1, 2, 4, 8, 7};
        heapSort(a, a.length - 1);
        prettyPrint(a);
    }
}
