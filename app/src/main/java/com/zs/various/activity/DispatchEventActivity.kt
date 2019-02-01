package com.zs.various.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import com.zs.various.R
import kotlinx.android.synthetic.main.activity_dispatch_layout.*

/**
 * Created by zs
 * Date：2018年 10月 24日
 * Time：16:14
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class DispatchEventActivity : AppCompatActivity() {

    /**
     *
     * 整体呈现U型图

    dispatchTouchEvent：事件分发
    从外层的ViewGroup传递到内层的View，如果当前布局不处理默认传递给下一层，
    返回super.dispatchTouchEvent(ev) 才是往下走，返回true 或者 false 事件就被消费了（终止传递）
    返回true时当前这层布局消费了，接下来会到当前布局的onTouchEvent方法中，
    返回false，表示回传到父布局的onTouchEvent处理。

    onInterceptTouchEvent ：事件拦截
    Intercept 的意思就拦截，每个ViewGroup每次在做分发的时候，问一问拦截器要不要拦截（也就是问问自己这个事件要不要自己来处理）如果要自己处理那就在onInterceptTouchEvent方法中 return true就会交给自己的onTouchEvent的处理，如果不拦截就是继续往子控件往下传。默认是不会去拦截的，因为子View也需要这个事件，所以onInterceptTouchEvent拦截器return super.onInterceptTouchEvent()和return false是一样的，是不会拦截的，事件会继续往子View的dispatchTouchEvent传递。

    onTouchEvent：事件消费
    默认是最内层的view消费，如果，内层的view没有处理，就往父控件传递，如果都没有处理的，回到最外层的activity
    返回 return super.onTouchEvent(event)，如果添加了onClickListener事件，会走到点击事件处理，如果没有返回给父控件的onTouchEvent
    返回 true 自己消费，也不会传到点击事件中
    返回 false 返回给父控件的onTouchEvent处理，父控件不处理再往上面传递
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch_layout)

        bottom_view?.setOnClickListener {

            Log.d("My_Log", " ============ bottom_view =============")

        }

        center_view?.setOnClickListener {

            Log.d("My_Log", " ============ center_view =============")

        }

        top_view?.setOnClickListener {

            Log.d("My_Log", " ============ top_view =============")

        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_DOWN) {
            Log.d("My_Log", " activity ====  onTouchEvent ACTION_DOWN ")
//            return true
        } else if (event?.action == MotionEvent.ACTION_MOVE) {
            Log.d("My_Log", " activity ====  onTouchEvent ACTION_MOVE ")
//            return true
        } else if (event?.action == MotionEvent.ACTION_UP) {
            Log.d("My_Log", " activity ====  onTouchEvent ACTION_UP ")
//            return true
        }
        return super.onTouchEvent(event)
    }

}
