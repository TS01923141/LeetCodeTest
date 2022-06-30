package com.example.leetcodetest

import org.junit.Assert
import org.junit.Test
import kotlin.math.max

class BinaryTreeTest {
    //二元樹取得深度
    @Test
    fun maximumDepthOfBinaryTree() {
        val treeNode = TreeNode(3).apply {
            left = TreeNode(2).apply {
                left = TreeNode(5)
                right = TreeNode(8)
            }
            right = TreeNode(4)
        }
        traverse(treeNode)
        Assert.assertEquals(depth, 3)
    }

    var depth: Int = 0
    var currentDepth = 0
    fun traverse(node: TreeNode?) {
        if (node == null) return
        currentDepth +=1
        depth = max(depth, currentDepth)
        //left
        traverse(node.left)
        //right
        traverse(node.right)
        currentDepth -= 1
    }

    @Test
    fun binaryTreePreorderTraversal() {
        val treeNode = TreeNode(3).apply {
            left = TreeNode(2).apply {
                right = TreeNode(8)
            }
            right = TreeNode(4).apply {
                left = TreeNode(5)
            }
        }
        preorderTraversal(treeNode)
        Assert.assertEquals(list.toList(), listOf(3,2,8,4,5))
    }

    var list : MutableList<Int> = mutableListOf()
    fun preorderTraversal(node: TreeNode?) {
        if (node == null) return
        list.add(node.`val`)
        //left
        preorderTraversal(node.left)
        //right
        preorderTraversal(node.right)
    }

    @Test
    fun diameterOfBinaryTreeTest() {
        val treeNode = TreeNode(1).apply {
            left = TreeNode(2).apply {
                left = TreeNode(4)
                right = TreeNode(5)
            }
            right = TreeNode(3)
        }
        diameterOfBinaryTree(treeNode)
        Assert.assertEquals(maxDepth, 3)
    }

    var maxDepth = 0
    //左邊最長+右邊最長
    fun diameterOfBinaryTree(node: TreeNode?): Int {
        if (node == null) return 0
        //left
        var leftLength = diameterOfBinaryTree(node.left)
        //right
        var rightLength = diameterOfBinaryTree(node.right)
        val currentDiameter = leftLength + rightLength
        maxDepth = max(currentDiameter, maxDepth)
        //左跟右取大的
        return 1 + max(leftLength, rightLength)
    }
}

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}