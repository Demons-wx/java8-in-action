package online.wangxuan.algorithm;

/**
 * 大顶堆
 * @author wangxuan
 * @date 2020/4/6 11:02 AM
 */

public class Heap {

    // 从1开始存储
    private int[] a;
    private int n;
    private int count;

    public Heap(int capacity) {
        this.a = new int[capacity];
        this.n = capacity;
        this.count = 0;
    }

    public void insert(int data) {
        if (count >= n) return;
        a[++count] = data;
        int i = count;
        while (i / 2 > 0 && a[i / 2] < a[i]) {
            swap(a, i, i / 2);
            i = i / 2;
        }
    }

    public void removeMax() {
        if (count == 0) return;
        a[1] = a[count--];
        heapify(a, 1, count);
    }

    /**
     * 从上往下堆化的过程
     * @param a 数组
     * @param i 待堆化的元素
     * @param count 堆长度
     */
    private static void heapify(int[] a, int i, int count) {
        while (true) {
            int maxPos = i;
            if (i * 2 <= count && a[i * 2] > a[maxPos]) maxPos = i * 2;
            if (i * 2 + 1 <= count && a[i * 2 + 1] > a[maxPos]) maxPos = i * 2 + 1;
            if (maxPos == i) return;
            swap(a, i, maxPos);
            i = maxPos;
        }
    }

    public static void heapSort(int[] a, int n) {
        // 建堆
        buildHeap(a, n);
        int k = n;
        while (k > 1) {
            swap(a, k, 1);
            --k;
            heapify(a, 1, k);
        }
    }

    private static void buildHeap(int[] a, int n) {
        for (int i = n / 2; i >= 1; i--) {
            heapify(a, i, n);
        }
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

    public static void main(String[] args) {
        int[] a = {0, 3, 5, 6, 9, 1, 2, 4, 8, 7};
        heapSort(a, a.length - 1);
        prettyPrint(a);
    }

}
