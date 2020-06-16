package online.wangxuan.algorithm;

import java.util.Random;

/**
 * @author wangxuan
 * @date 2020/4/11 11:00 AM
 */

public class SkipList {

    private static final int MAX_LEVEL = 16;
    private int levelCount = 1;

    private Node head = new Node(MAX_LEVEL);
    private Random r = new Random();

    public void insert(int value) {
        int level = head.forwards[0] == null ? 1 : randomLevel();
        // 每次只加一层
        if (level > levelCount) {
            level = ++ levelCount;
        }

        Node newNode = new Node(level);
        newNode.data = value;
        newNode.maxLevel = level;
        // 记录要更新的层数，表示新节点要更新到哪几层
        Node[] update = new Node[level];
        for (int i = 0; i < level; i++) {
            update[i] = head;
        }

        // 从最大层开始找，找到前驱结点，移动到下层继续找
        Node p = head;
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            // levelCount 会 > level，所以加上判断
            if (level > i) {
                update[i] = p;
            }
        }

        for (int i = 0; i < level; i++) {
            newNode.forwards[i] = update[i].forwards[i];
            update[i].forwards[i] = newNode;
        }
    }

    private int randomLevel() {
        int level = 1;
        for (int i = 1; i < MAX_LEVEL; i++) {
            if (r.nextInt() % 2 == 1) {
                level++;
            }
        }
        return level;
    }

    public Node find(int value) {
        Node p = head;
        // 找到前驱结点
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
        }

        if (p.forwards[0] != null && p.forwards[0].data == value) {
            return p.forwards[0];
        }

        return null;
    }

    public void delete(int value) {
        Node[] update = new Node[levelCount];
        Node p = head;
        // 找到每一层的前驱节点
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            update[i] = p;
        }

        // 找到要删除的节点
        if (p.forwards[0] != null && p.forwards[0].data == value) {
            for (int i = levelCount - 1; i >= 0; i--) {
                // 如果要删除的节点在该层被引用，需要删除引用
                if (update[i].forwards[i] != null && update[i].forwards[i].data == value) {
                    update[i].forwards[i] = update[i].forwards[i].forwards[i];
                }
            }
        }
    }

    /**
     * 打印每个节点数据和最大层数
     */
    public void printAll() {
        Node p = head;
        while (p.forwards[0] != null) {
            System.out.print(p.forwards[0] + " ");
            p = p.forwards[0];
        }
        System.out.println();
    }

    /**
     * 跳表的节点，每个节点记录当前节点数据和所在层数数据
     */
    public class Node {
        private int data = -1;
        private Node[] forwards;
        private int maxLevel = 0;

        public Node(int level) {
            forwards = new Node[level];
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{ data: ");
            builder.append(data);
            builder.append("; levels: ");
            builder.append(maxLevel);
            builder.append(" }");
            return builder.toString();
        }
    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        skipList.insert(1);
        skipList.insert(2);
        skipList.insert(6);
        skipList.insert(7);
        skipList.insert(8);
        skipList.insert(3);
        skipList.insert(4);
        skipList.insert(5);
        skipList.printAll();

        skipList.find(5);

    }
}
