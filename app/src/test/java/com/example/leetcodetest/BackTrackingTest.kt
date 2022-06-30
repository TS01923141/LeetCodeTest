package com.example.leetcodetest

import org.junit.Assert
import org.junit.Test
import java.util.*

class BackTrackingTest {
    @Test
    fun permutationsTest() {
        val result = listOf<List<Int>>(
            listOf(1,2,3),
            listOf(1,3,2),
            listOf(2,1,3),
            listOf(2,3,1),
            listOf(3,1,2),
            listOf(3,2,1),
        )
        Assert.assertEquals(permutations(intArrayOf(1,2,3)), result)
    }

    val result : MutableList<List<Int>> = mutableListOf()
    fun permutations(nums: IntArray): List<List<Int>> {
        var track: MutableList<Int> = mutableListOf()
        var used = BooleanArray(nums.size)
        backtrack(nums, track, used)
        return result
    }

    fun backtrack(nums: IntArray, track: MutableList<Int>, used: BooleanArray) {
        //結束條件
        if (track.size == nums.size) {
            result.add(track.toList())
            return
        }

        //分支
        for (i in nums.indices) {
            //排除不成立的選項
            if (used[i]) continue
            //加入選擇
            track.add(nums[i])
            used[i] = true
            backtrack(nums, track, used)
            //刪除選擇
            track.remove(nums[i])
            used[i] = false
        }
    }

    @Test
    fun solveNQueensTest() {
        Assert.assertEquals(listOf(listOf(".Q..","...Q","Q...","..Q."), listOf( "..Q.","Q...","...Q",".Q..")), solveNQueens(4))
    }

    //N皇后
    //行列上只能有一個皇后，皇后的九宮格不能有其他皇后
    //皇后用"Q"表示，其他用"."
    fun solveNQueens(n: Int): List<List<String>> {
        val resultBoard : MutableList<List<String>> = mutableListOf()
        val board = Array(n) { CharArray(n) { '.' } }

        fun isValid(row: Int, col: Int): Boolean {
            //檢查row
            for (i in 0 until row) {
                if (board[i][col] == 'Q') return false
            }
            //檢查右上
            var i = row - 1; var j = col + 1
            while (i >= 0 && j < n) {
                if (board[i--][j++] == 'Q') return false
            }
            //檢查左上
            i = row - 1; j = col - 1
            while (i >= 0 && j >= 0) {
                if (board[i--][j--] == 'Q') return false
            }
            return true
        }

        fun backtrackQueen(row: Int) {
            if (row == n) {
                resultBoard.add(board.map { String(it) })
                return
            }
            for (col in 0 until n) {
                if (!isValid(row, col)) continue
                board[row][col] = 'Q'
                backtrackQueen(row + 1)
                board[row][col] = '.'
            }
        }

        backtrackQueen(0)
        return resultBoard
    }

    @Test
    fun subsetsTest() {
        val result = listOf(
            listOf(),
            listOf(1),
            listOf(1,2),
            listOf(1,2,3),
            listOf(1,3),
            listOf(2),
            listOf(2,3),
            listOf(3)
        )
        Assert.assertEquals(result, subsets(intArrayOf(1,2,3)))
    }

    fun subsets(nums: IntArray): List<List<Int>> {
        var res = mutableListOf(listOf<Int>())
        var track = mutableListOf<Int>()

        fun backtrack(nums: IntArray, start: Int) {
            for (i in start until nums.size) {
                //加入
                track.add(nums[i])
                res.add(track.toList())
                backtrack(nums, i + 1)
                //刪除
                track.remove(nums[i])
            }
        }

        backtrack(nums, 0)
        return res
    }

    @Test
    fun combineTest() {
        val result = listOf(
            listOf(1,2),
            listOf(1,3),
            listOf(1,4),
            listOf(2,3),
            listOf(2,4),
            listOf(3,4),
        )
        Assert.assertEquals(combine(4, 2), result)
    }

    //1~n的數列內所有數量為k的組合
    fun combine(n: Int, k: Int): List<List<Int>> {
        var res = mutableListOf<List<Int>>()
        var track = mutableListOf<Int>()

        fun backtrack(start: Int, n: Int, k: Int) {
            if (k == track.size) {
                res.add(track.toList())
                return
            }

            for (i in start..n) {
                //加入
                track.add(i)
                backtrack(i + 1, n, k)
                //刪除
                track.removeLast()
            }
        }

        backtrack(1, n, k)
        return res
    }

    @Test
    fun subsetsWithDupTest() {
        val result = listOf(
            listOf(),
            listOf(1),
            listOf(1,2),
            listOf(1,2,2),
            listOf(2),
            listOf(2,2)
        )
        Assert.assertEquals(subsetsWithDup(intArrayOf(1,2,2)), result)
    }

    //取得所有不重複排列
    fun subsetsWithDup(nums: IntArray): List<List<Int>> {
        var res = mutableListOf<List<Int>>(listOf())
        var track = mutableListOf<Int>()

        fun backtrack(nums: IntArray, start: Int) {
            for (i in start until nums.size) {
                //跳過重複的值
                if (i > start && nums[i] == nums[i - 1]) continue
                track.add(nums[i])
                res.add(track.toList())
                backtrack(nums, i + 1)
                track.remove(nums[i])
            }
        }

        Arrays.sort(nums)
        backtrack(nums, 0)
        return res
    }

    @Test
    fun combinationSum2Test() {
        val result = listOf(
            listOf(1,1,6),
            listOf(1,2,5),
            listOf(1,7),
            listOf(2,6)
        )
        Assert.assertEquals(combinationSum2(intArrayOf(10,1,2,7,6,1,5), 8), result)
    }

    //用candidates內的數字找出能組合出target，candidates[]內每個值只能使用一次
    fun combinationSum2(candidates: IntArray, target: Int): List<List<Int>> {
        var res = mutableListOf<List<Int>>()
        var track = mutableListOf<Int>()
        var sum = 0

        fun backtrack(start: Int) {
            if (sum == target) {
                res.add(track.toList())
                return
            } else if (sum > target) {
                return
            }

            for (i in start until candidates.size) {
                if (i > start && candidates[i] == candidates[i - 1]) continue
                track.add(candidates[i])
                sum+= candidates[i]
                backtrack(i + 1)
                track.remove(candidates[i])
                sum-= candidates[i]
            }
        }

        Arrays.sort(candidates)
        backtrack(0)
        return res
    }

    @Test
    fun permuteUniqueTest() {
        val result = listOf(
            listOf(1,1,2),
            listOf(1,2,1),
            listOf(2,1,1)
        )
        val result2 = listOf(
            listOf(1,1,2,2),
            listOf(1,2,1,2),
            listOf(1,2,2,1),
            listOf(2,1,1,2),
            listOf(2,1,2,1),
            listOf(2,2,1,1)
        )
//        Assert.assertEquals(result, permuteUnique(intArrayOf(1,1,2)))
        Assert.assertEquals(result2, permuteUnique(intArrayOf(2,2,1,1)))
    }

    fun permuteUnique(nums: IntArray): List<List<Int>> {
        var res = mutableListOf<List<Int>>()
        var used = BooleanArray(nums.size)
        var track = mutableListOf<Int>()

        fun backtrack() {
            //完成條件
            if (nums.size == track.size) {
                res.add(track.toList())
                return
            }

            //迴圈
            for (i in nums.indices) {
                //條件
                if (used[i]) continue
                if (i > 0 && nums[i - 1] == nums[i] && !used[i - 1]) continue
                //新增
                track.add(nums[i])
                used[i] = true
                //往下一層
                backtrack()
                //離開
                used[i] = false
                track.removeLast()
            }
        }

        Arrays.sort(nums)
        backtrack()
        return res
    }

    @Test
    fun combinationSumTest() {
        val result = listOf(
            listOf(2,2,3),
            listOf(7)
        )
        Assert.assertEquals(result, combinationSum(intArrayOf(2,3,6,7), 7))
    }

    fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
        var res : MutableList<List<Int>> = mutableListOf()
        var track = mutableListOf<Int>()
        var sum = 0

        fun backtrack(start: Int) {
            if (sum > target) return
            if (sum == target) {
                res.add(track.toList())
                return
            }

            for (i in start until candidates.size) {
                sum += candidates[i]
                track.add(candidates[i])
                backtrack(i)
                track.removeAt(track.size-1)
                sum -= candidates[i]
            }
        }

        backtrack(0)
        return res
    }
}