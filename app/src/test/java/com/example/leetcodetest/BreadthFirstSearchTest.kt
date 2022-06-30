package com.example.leetcodetest

import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.collections.HashSet

class BreadthFirstSearchTest {

    @Test
    fun minDepthTest() {
        var root = TreeNode(3).apply {
            left = TreeNode(9)
            right = TreeNode(20).apply {
                left = TreeNode(15)
                right = TreeNode(7)
            }
        }
        Assert.assertEquals(minDepth(root), 2)
    }

    fun minDepth(root: TreeNode?): Int {
        if (root == null) return 0
        var q: Queue<TreeNode> = ArrayDeque()
        q.offer(root)
        var depth = 1
        while (q.isNotEmpty()) {
            var sz = q.size
            for (i in 0 until sz) {
                val cur = q.poll()
                //條件
                if (cur.left == null && cur.right == null) return depth
                //left
                if (cur.left != null) q.offer(cur.left)
                //right
                if (cur.right != null) q.offer(cur.right)
            }
            depth++
        }
        return depth
    }

    @Test
    fun openLockTest() {
        Assert.assertEquals(openLock(arrayOf("0201","0101","0102","1212","2002"), "0202"), 6)
        Assert.assertEquals(twoWayOpenLock(arrayOf("0201","0101","0102","1212","2002"), "0202"), 6)
    }

    fun openLock(deadends: Array<String>, target: String): Int {
        var deads = HashSet<String>().apply {
            addAll(deadends)
        }
        var visited = HashSet<String>()
        var q : Queue<String> = ArrayDeque()
        var step = 0
        q.offer("0000")
        visited.add("0000")

        fun plusOne(s: String, position: Int): String {
            var charArray = s.toCharArray()
            if (charArray[position] == '9') charArray[position] = '0'
            else charArray[position] = charArray[position]+1
            return String(charArray)
        }

        fun minusOne(s: String, position: Int): String {
            var charArray = s.toCharArray()
            if (charArray[position] == '0') charArray[position] = '9'
            else charArray[position] = charArray[position]-1
            return String(charArray)
        }

        while (q.isNotEmpty()) {
            val sz = q.size
            for (i in 0 until sz) {
                val cur = q.poll()
                //條件
                if (deads.contains(cur)) continue
                if (target == cur) return step
                for (i in 0..3) {
                    //plus
                    val up = plusOne(cur, i)
                    if (!visited.contains(up)) {
                        q.offer(up)
                        visited.add(up)
                    }
                    //minus
                    val down = minusOne(cur, i)
                    if (!visited.contains(down)) {
                        q.offer(down)
                        visited.add(down)
                    }
                }
            }
            step++
        }
        return -1
    }

    fun twoWayOpenLock(deadends: Array<String>, target: String): Int {
        var deads = HashSet<String>().apply {
            addAll(deadends)
        }
        var visited = HashSet<String>()
        var q1 = HashSet<String>()
        var q2 = HashSet<String>()
        var step = 0
        q1.add("0000")
        q2.add(target)
        visited.add("0000")

        fun plusOne(s: String, position: Int): String {
            var charArray = s.toCharArray()
            if (charArray[position] == '9') charArray[position] = '0'
            else charArray[position] = charArray[position]+1
            return String(charArray)
        }

        fun minusOne(s: String, position: Int): String {
            var charArray = s.toCharArray()
            if (charArray[position] == '0') charArray[position] = '9'
            else charArray[position] = charArray[position]-1
            return String(charArray)
        }

        while (q1.isNotEmpty() && q2.isNotEmpty()) {
            //保存q1執行結果
            var temp = HashSet<String>()
            for (cur in q1) {
                //條件
                if (deads.contains(cur)) continue
                //q1, q2之間有交集，代表是答案
                if (q2.contains(cur)) return step
                visited.add(cur)
                for (i in 0..3) {
                    //plus
                    val up = plusOne(cur, i)
                    if (!visited.contains(up)) temp.add(up)
                    //minus
                    val down = minusOne(cur, i)
                    if (!visited.contains(down)) temp.add(down)
                }
            }
            step++
            //對調準備下一輪
            q1 = q2
            q2 = temp
        }
        return -1
    }
}