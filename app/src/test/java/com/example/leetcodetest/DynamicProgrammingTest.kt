package com.example.leetcodetest

import org.junit.Assert
import org.junit.Test
import java.util.*

class DynamicProgrammingTest {
    @Test
    fun fibTest() {
        Assert.assertEquals(fib(2), 1)
        Assert.assertEquals(fib(3), 2)
    }

    fun fib(n: Int): Int {
        if (n < 2) return n
//        if (n <= 0) return 0
//        if (n == 1) return 1
//        return fib(n-1) + fib(n-2)
        var f0 = 1
        var f1 = 0
        var fn = 0
        for(i in 2..n) {
            fn = f0 + f1
            f1 = f0
            f0 = fn
        }
        return fn
    }

    @Test
    fun coinChangeTest() {
        //給予一個硬幣組合跟一個數字，求最少硬幣的組合
        //找出最小集合 -> n = n + coin
        //從1開始往上組
        Assert.assertEquals(coinChange(intArrayOf(1,2,5), 11), 3)
    }

    fun coinChange(coins: IntArray, amount: Int): Int {
        var list = IntArray(amount+1)
        //用amount+1填充，無法成立的組合都會是amount+1
        Arrays.fill(list, amount+1)
        list[0] = 0
        //0到amount取得所有最小組合
        for (i in 0..amount){
            //取得該數字最小組合
            for (coin in coins) {
                //數字爆掉，跳過
                if (i - coin < 0) continue
                //比較目前的最小組合跟現在的組合誰比較小
                list[i] = Math.min(list[i] , 1 + list[i - coin])
            }
        }
        return if (list[amount] > amount) return -1 else list[amount]
    }
}