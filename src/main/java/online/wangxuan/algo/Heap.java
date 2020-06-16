package online.wangxuan.algo;

import java.util.Arrays;
import java.util.List;

/**
 * 堆
 * - 堆是一个完全二叉树 (完全二叉树要求除了最后一层，其他层的节点个数都是满的，最后一层的节点都靠左排列)
 * - 堆中每一个节点都必须大于等于(或小于等于)其子树中的每个节点的值
 * @author xwangr
 * @date 2020/4/3
 */
public class Heap {

    // 从下标1开始存储数据
    private int[] a;
    // 堆可以存储的最大数据个数
    private int n;
    // 堆中已经存储的数据个数
    private int count;

    public Heap(int capacity) {
        this.a = new int[capacity];
        this.n = capacity;
        this.count = 0;
    }

    /**
     * 插入
     * 从下往上堆化 (大顶堆)
     * @param data 待插入元素
     */
    public void insert(int data) {
        // 堆满了
        if (count >= n) return;
        a[++count] = data;
        int i = count;
        while (i / 2 > 0 && a[i] > a[i / 2]) {
            swap(a, i, i / 2);
            i = i / 2;
        }
    }

    /**
     * 删除堆顶元素
     * 先将堆顶元素与最后一个元素交换，再从上往下堆化 (大顶堆)
     */
    public void removeMax() {
        if (count == 0) return;
        a[1] = a[count--];
        heapify(a, 1, count);
    }

    /**
     * 堆化
     * @param a 数组
     * @param i 待堆化数据下标
     * @param n 堆中数据个数
     */
    private static void heapify(int[] a, int i, int n) {
        while (true) {
            int maxPos = i;
            if (2 * i <= n && a[2 * i] > a[i]) maxPos = 2 * i;
            if (2 * i + 1 <= n && a[2 * i + 1] > a[maxPos]) maxPos = 2 * i + 1;
            if (maxPos == i) return;
            swap(a, i, maxPos);
            i = maxPos;
        }
    }

    /**
     * 堆排序
     * @param a 数组中的数据从下标1到n
     * @param n 数据个数
     */
    public static void heapSort(int[] a, int n) {
        // 1. 建堆过程 O(n)
        buildHeap(a, n);

        // 2. 下沉排序过程 O(nlogn)
        int k = n;
        while (k > 1) {
            swap(a, 1, k--);
            heapify(a, 1, k);
        }
    }

    private static void buildHeap(int[] a, int n) {
        for (int i = n / 2; i >= 1; i--) {
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
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
        }
    }

    public static void main(String[] args) {
//        int[] a = {0, 2, 5, 6, 1, 3, 7, 9, 8, 4, 2};
//        heapSort(a, a.length - 1);
//        prettyPrint(a);

        List<Integer> a = Arrays.asList(4, 3, 2, 1);
        Heap heap = new Heap(a.size() + 1);
        a.forEach(heap::insert);
        heap.removeMax();
        prettyPrint(heap.a);
    }
}
