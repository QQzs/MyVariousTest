package com.zs.various.util.extension

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * @Author: zs
 * @Date: 2020-05-22 16:07
 *
 * @Description:
 */
fun delayTime(time: Long): Observable<Long> {
    return Observable.just(time).delay(time, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
}