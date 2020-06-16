package online.wangxuan.algo;

/**
 * @author xwangr
 * @date 2020/3/31
 */
public class SkipList {

    private static final float SKIPLIST_P = 0.5f;
    private static final int MAX_LEVEL = 16;

    private static int levelCount = 1;
    // 带头链表
    private static Node head = new Node();

    public static Node find(int value) {
        Node p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
        }

        if (p.forwards[0] != null && p.forwards[0].data == value) {
            return p.forwards[0];
        } else {
            return null;
        }
    }

    public static void insert(int value) {

        // 随机一个层高，在小于level的每一层索引中都需要增加这个节点
        int level = randomLevel();

        Node newNode = new Node();
        newNode.data = value;
        newNode.maxLevel = level;

        // 记录每一层小于插入值的最大值节点，也就是待插入节点的prev
        Node[] update = new Node[level];
        for (int i = 0; i < level; i++) {
            update[i] = head;
        }

       // 找到每一层小于插入值的最大值节点
        Node p = head;
        for (int i = level - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            update[i] = p;
        }

        for (int i = 0; i < level; i++) {
            // 记录插入节点在第i层的next指针
            newNode.forwards[i] = update[i].forwards[i];
            // 前一个节点的next指针，指向插入节点
            update[i].forwards[i] = newNode;
        }

        // 更新层高
        if (levelCount < level) {
            levelCount = level;
        }
    }

    public static void delete(int value) {

        // 记录每一层小于删除值的最大值节点，也就是待删除节点的prev
        Node[] update = new Node[levelCount];
        Node p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            update[i] = p;
        }

        // 如果待删除的节点存在
        if (p.forwards[0] != null && p.forwards[0].data == value) {
            for (int i = levelCount - 1; i >= 0; --i) {
                // 删除该节点在每一层的索引 (如果有的话)
                if (update[i].forwards[i] != null && update[i].forwards[i].data == value) {
                    update[i].forwards[i] = update[i].forwards[i].forwards[i];
                }
            }
        }

        while (levelCount > 1 && head.forwards[levelCount] == null) {
            levelCount--;
        }
    }

    private static int randomLevel() {
        int level = 1;
        while (Math.random() < SKIPLIST_P && level < MAX_LEVEL) {
            level += 1;
        }

        return level;
    }


    public static class Node {
        private int data = -1;

        // 表示当前节点位置的下一个节点所有层的数据，从上层切换到下层，就是数组下标-1
        // forwards[3]表示当前节点在第三层的下一个节点
        private Node forwards[] = new Node[MAX_LEVEL];
        private int maxLevel = 0;

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
}
