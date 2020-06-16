package online.wangxuan.algo;

/**
 * @author xwangr
 * @date 2020/3/25
 */
public class LinkedListAlgo {

    // 单链表反转
    public static Node reserve(Node head) {
        Node prev = null, next;
        while (head != null) {
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    // 检测环 快慢指针，类似于在环形跑道跑步，快的总会重新遇到慢的
    public static boolean checkCircle(Node list) {
        Node slow = list, quick = list;
        while (quick != null && quick.next != null) {
            slow = slow.next;
            quick = quick.next.next;

            if (slow == quick) {
                return true;
            }
        }
        return false;
    }

    public static String prettyPrint(Node head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.getData());
            head = head.next;
            if (head != null) {
                sb.append("->");
            }
        }
        return sb.toString();
    }

    public static class Node {
        private int data;
        private Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return data;
        }
    }

    public static void main(String[] args) {
        Node a = new Node(1, null);
        Node b = new Node(2, a);
        Node c = new Node(3, b);
        Node d = new Node(4, c);

//        System.out.println(prettyPrint(d));
//        System.out.println(prettyPrint(reserve(d)));
        System.out.println(prettyPrint(reserve(d)));
    }
}
