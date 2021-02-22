package com.tonglxer.leetcode.hot;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Author Tong LinXing
 * @date 2021/2/22
 */
public class LoopLinkedList {
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class ListNode {
        public int val;
        public ListNode next;
    }

    /**
     * 判断链表是否有环
     * 快慢指针，若有环则一定相遇且环的长度即为快指针多走的路程
     *
     * @param head
     * @return
     */
    public static boolean isLoop(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        int loopLength = 0;
        while (fast!=null && fast.next!=null) {
            slow = slow.next;
            fast = fast.next.next;
            // 可以记录环的长度
            ++loopLength;
            if (slow == fast) {
                System.out.println("The loop length is: " + loopLength);
                return true;
            }
        }
        return false;
    }

    /**
     * 求有环链表的入环口
     * 设入环口为点 X, 则：
     * head到X的距离为 a
     * X到交点的距离为 b
     * 交点到X的距离为 c
     * 一、b+c = 环的长度
     * 二、2*(a+b) = a+b+c+b, 快指针比慢指针多走一倍路
     * 可得：a = c
     *
     * @param head
     * @return
     */
    public static ListNode findLoopHead(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast!=null && fast.next!=null) {
            slow = slow.next;
            fast = fast.next.next;
            // 可以记录环的长度
            if (slow == fast) {
                break;
            }
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

}
