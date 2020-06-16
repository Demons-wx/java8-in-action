package online.wangxuan.algorithm;

/**
 * @author wangxuan
 * @date 2020/3/28 8:14 PM
 */

public class LRUBaseLinkedList<T> {

    // 默认容量
    private final static Integer DEFAULT_CAPACITY = 10;
    // 头结点
    private Node<T> headNode;
    // 尾结点
    private Node<T> tailNode;
    // 链表长度
    private Integer length;
    // 链表容量
    private Integer capacity;

    public LRUBaseLinkedList() {
        this.headNode = new Node<>();
        this.capacity = DEFAULT_CAPACITY;
        this.length = 0;
    }

    public LRUBaseLinkedList(Integer capacity) {
        this.headNode = new Node<>();
        this.capacity = capacity;
        this.length = 0;
    }

    public void add(T data) {
        Node node = findPreNode(data);
        if (node != null) {
            removeNextNode(node);
            insertNodeBegin(data);
        } else {
            if (length >= capacity) {
                deleteLatestNode();
            }
            insertNodeBegin(data);
        }
    }

    private void deleteLatestNode() {

    }

    private void insertNodeBegin(T data) {
        Node next = headNode.getNext();
        Node newHead = new Node(data, next);
        headNode.setNext(newHead);
        if (length == 0) {
            tailNode = newHead;
        }
        length++;
    }

    private void removeNextNode(Node node) {
        Node afterNode = node.getNext().getNext();
        if (afterNode == null) {
            tailNode = node;
        }
        node.setNext(afterNode);
        length--;
    }

    private Node findPreNode(T data) {
        Node head = headNode;
        while (head.getNext() != null) {
            if (head.getNext().getElement().equals(data)) {
                return head;
            }
            head = head.getNext();
        }
        return null;
    }

    public class Node<T> {

        private T element;
        private Node next;

        public Node() {
            this.next = null;
        }

        public Node(T element) {
            this.element = element;
        }

        public Node(T element, Node next) {
            this.element = element;
            this.next = next;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
