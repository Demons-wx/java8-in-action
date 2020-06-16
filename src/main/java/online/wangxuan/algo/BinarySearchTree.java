package online.wangxuan.algo;

/**
 * @author xwangr
 * @date 2020/4/2
 */
public class BinarySearchTree {

    private static Node tree;

    public static void insert(int data) {
        if (tree == null) {
            tree =  new Node(data);
            return;
        }

        Node p = tree;
        while (p != null) {
            if (data > p.data) {
                if (p.right == null) {
                    p.right = new Node(data);
                    return;
                }
                p = p.right;
            } else { // data < p.data
                if (p.left == null) {
                    p.left = new Node(data);
                    return;
                }
                p = p.left;
            }
        }
    }

    public static Node find(int data) {
        Node p = tree;
        while (p != null) {
            if (p.data == data) return p;
            if (p.data > data) {
                p = p.left;
            } else {
                p = p.right;
            }
        }

        return null;
    }

    public static void delete(int data) {
        Node p = tree;
        Node pp = null;
        while (p != null && p.data != data) {
            pp = p;
            if (data > p.data) p = p.right;
            else p = p.left;
        }

        if (p == null) return;

        // 要删除的节点有两个子节点
        if (p.left != null && p.right != null) {    // 查找右子树中最小的节点
            Node minP = p.right;
            Node minPP = p; // minPP表示min的父节点
            while (minP.left != null) {
                minPP = minP;
                minP = minP.left;
            }

            p.data = minP.data; // 将minP的数据替换到p中
            p = minP;   // 下面就变成了删除minP
            pp = minPP;
        }

        // 删除节点是叶子节点或者仅有一个子节点
        Node child;
        if (p.left != null) child = p.left;
        else if (p.right != null) child = p.right;
        else child = null;

        if (pp == null) tree = child; // 删除的是根节点
        else if (pp.left == p) pp.left = child;
        else pp.right = child;
    }

    public static int maxDepth(Node root) {
        if (root == null) return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }

    public static void preOrder(Node root) {
        if (root == null) return;
        System.out.print(root.data);
        preOrder(root.left);
        preOrder(root.right);
    }

    public static void inOrder(Node root) {
        if (root == null) return;
        inOrder(root.left);
        System.out.print(root.data);
        inOrder(root.right);
    }

    public static void postOrder(Node root) {
        if (root == null) return;
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.data);
    }

    public static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }
}
