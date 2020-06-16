package online.wangxuan.algorithm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangxuan
 * @date 2020/3/25 9:14 PM
 */

public class LinkedListAlgorithm {

    public static class ListNode {
        int val;
        ListNode next;
        public ListNode(int val) {
            this.val = val;
        }
    }

    public static ListNode reserve(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }

        return prev;
    }

    // Merge Two Sorted Lists
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode soldier = new ListNode(0);
        ListNode p = soldier;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }

        if (l1 == null) p.next = l2;
        if (l2 == null) p.next = l1;
        return soldier.next;
    }

    // 删除倒数第K个结点
    public static ListNode deleteLastKth(ListNode l, int k) {
        ListNode fast = l;
        while (fast.next != null && --k > 0) {
            k--;
            fast = fast.next;
        }

        // 链表长度小于k
        if (k > 0) {
            return l;
        }

        ListNode slow = l;
        ListNode prev = null;
        while (fast.next != null) {
            fast = fast.next;
            prev = slow;
            slow = slow.next;
        }
        if (prev != null) {
            prev.next = prev.next.next;
        } else {
            l = l.next;
        }
        return l;
    }

    public static void prettyPrint(ListNode l) {
        StringBuilder sb = new StringBuilder();
        while (l != null) {
            sb.append(l.val);
            l = l.next;
            if (l != null) {
                sb.append("->");
            }

        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(3);
        ListNode d = new ListNode(4);
        ListNode e = new ListNode(5);

        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;

        prettyPrint(a);
        prettyPrint(reserve(a));
    }

}
