package com.zs.various.util

/**
 * Created by zs
 * Date：2018年 10月 08日
 * Time：15:41
 * —————————————————————————————————————
 * About: 单例模式
 * —————————————————————————————————————
 */
class InstanceKotlinUtil {

    companion object{
        @Volatile
        private var mUtil: InstanceKotlinUtil? = null
        /**
         * 两次判空实现单例
         * @return
         */
        val instance1: InstanceKotlinUtil?
            get() {
                if (mUtil == null) {
                    synchronized(InstanceKotlinUtil::class.java) {
                        if (mUtil == null) {
                            mUtil = InstanceKotlinUtil()
                        }
                    }
                }
                return mUtil
            }

        /**
         * 静态内部类实现单例
         * @return
         */
        val instance2: InstanceKotlinUtil
            get() = TestHolder.instance

        private object TestHolder {
            val instance: InstanceKotlinUtil = InstanceKotlinUtil()
        }
    }
}