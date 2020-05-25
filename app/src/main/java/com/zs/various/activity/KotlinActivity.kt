package com.zs.various.activity

import com.zs.various.R
import com.zs.various.base.BaseActivity
import com.zs.various.util.LogUtil
import com.zs.various.util.extension.delayTime
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * @Author: zs
 * @Date: 2020-05-22 15:26
 *
 * @Description:
 */
class KotlinActivity : BaseActivity(){

    private var mBackA = 0
    private var mBackB = 0

    override fun setLayoutId() = R.layout.activity_kotlin

    override fun initView() {
    }

    override fun initData() {
        delayTime(1000L).subscribe{
//            start()
            startOther()
        }
    }

    /**
     * 子协程
     */
    fun start() = runBlocking {
        LogUtil.logShow("=== start ===")
        val time = measureTimeMillis {
            var a = async { actionA() }
            var b = async { actionB() }
            a.await()
            b.await()
        }
        LogUtil.logShow("time = $time")
    }

    suspend fun actionA(): Int{
        delay(1000L)
        mBackA = 10
        return 10
    }

    suspend fun actionB(): Int{
        delay(2000L)
        mBackB = 20
        return 20
    }

    /**
     * 顶层协程
     */
    private fun startOther() = runBlocking {
        LogUtil.logShow("=== startOther ===")
        var job1 = GlobalScope.launch {
            actionA()
        }
        var job2 = GlobalScope.launch {
            actionB()
        }
        job1.join()
        job2.join()
        LogUtil.logShow("Back = ${mBackA + mBackB}")
    }


}
