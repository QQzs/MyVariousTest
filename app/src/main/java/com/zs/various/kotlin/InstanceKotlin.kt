package com.zs.various.kotlin

/**
 * @Author: zs
 * @Date: 20/12/23 上午8:29
 * @Description:
 */
class InstanceKotlin {

    companion object{
        @Volatile
        private var mUtil: InstanceKotlin? = null
        /**
         * 两次判空实现单例
         * @return
         */
        val instance1: InstanceKotlin?
            get() {
                if (mUtil == null) {
                    synchronized(InstanceKotlin::class.java) {
                        if (mUtil == null) {
                            mUtil = InstanceKotlin()
                        }
                    }
                }
                return mUtil
            }

        /**
         * 静态内部类实现单例
         * @return
         */
        val instance2: InstanceKotlin
            get() = TestHolder.instance

        private object TestHolder {
            val instance: InstanceKotlin = InstanceKotlin()
        }
    }
}