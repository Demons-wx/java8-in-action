package online.wangxuan.algo;

/**
 * @author xwangr
 * @date 2020/5/9
 */
public class Queue {

    public class ArrayQueue {
        // 数组items, 数组大小n
        private String[] items;
        private int n = 0;

        // head表示队头下标, tail表示队尾下标
        private int head = 0;
        private int tail = 0;

        public ArrayQueue(int capacity) {
            this.items = new String[capacity];
            this.n = capacity;
        }

        public boolean enqueue(String item) {
            if (tail == n) {
                if (head == 0) return false;
                // 数据搬移
                for (int i = head; i < tail; i++) {
                    items[i - head] = items[i];
                }

                tail -= head;
                head = 0;
            }

            items[tail++] = item;
            return true;
        }

        public String dequeue() {
            if (head == tail) return null;
            return items[head++];
        }
    }

    /**
     * 循环队列必须留出一个空位，不然无法区分队空和队满
     */
    public class CircularQueue {
        private String[] items;
        private int n;
        private int head = 0;
        private int tail = 0;

        public CircularQueue(int capacity) {
            items = new String[capacity];
            n = capacity;
        }

        public boolean enqueue(String item) {
            if ((tail + 1) % n == head) return false;
            items[tail] = item;
            tail = (tail + 1) % n;
            return true;
        }

        public String dequeue() {
            if (head == tail) return null;
            String ret = items[head];
            head = (head + 1) % n;
            return ret;
        }
    }
}
