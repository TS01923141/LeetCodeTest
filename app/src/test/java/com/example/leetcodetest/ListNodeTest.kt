package com.example.leetcodetest

import org.junit.Assert
import org.junit.Test

class ListNodeTest {
    @Test
    fun mergeTwoListsTest() {
        val list1 = ListNode(1).apply {
            next = ListNode(2).apply {
                next = ListNode(4)
            }
        }
        val list2 = ListNode(1).apply {
            next = ListNode(3).apply {
                next = ListNode(4)
            }
        }
        var result: ListNode? = ListNode(1).apply {
            next = ListNode(1).apply {
                next = ListNode(2).apply {
                    next = ListNode(3).apply {
                        next = ListNode(4).apply {
                            next = ListNode(4)
                        }
                    }
                }
            }
        }
        var node: ListNode? = mergeTwoLists(list1, list2)
        while (result != null && node != null) {
            Assert.assertEquals(node.`val`, result.`val`)
            result = result.next
            node = node.next
        }
    }

    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        if (list1 == null && list2 == null) return null
        if (list1 == null) return list2
        if (list2 == null) return list1

        var res : ListNode = ListNode(-1)
        var resNode = res
        var node1 = list1
        var node2 = list2

        fun addToRes(node: ListNode) {
            resNode.next = node
            resNode = resNode.next!!
        }

        while (node1 != null && node2 != null) {
            if (node1.`val` > node2.`val`) {
                addToRes(node2)
                node2 = node2.next
            } else {
                addToRes(node1)
                node1 = node1.next
            }
        }
        if (node1 != null) addToRes(node1)
        else if (node2 != null) addToRes(node2)
        return res.next
    }
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}