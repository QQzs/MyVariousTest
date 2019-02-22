package com.zs.various.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import com.zs.various.R
import kotlinx.android.synthetic.main.activity_dispatch_event_layout.*

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
    return super.dispatchTouchEvent(ev) 才是往下走
    return true, 当前这层布局消费了（终止传递）
    return false，表示回传到父布局的onTouchEvent处理。

    onInterceptTouchEvent ：事件拦截
    每个ViewGroup每次在做分发的时候，问一问拦截器要不要拦截
    return true 拦截 ，就会交给自己的onTouchEvent的处理，
    默认是不会去拦截的，因为子View也需要这个事件，
    return super.onInterceptTouchEvent()和return false是一样的，事件会继续往子View的dispatchTouchEvent传递。

    onTouchEvent：事件消费
    默认是最内层的view先处理，内层的view没有处理，就往父控件传递，如果都没有处理的，回到最外层的activity
    return super.onTouchEvent(event) 和 return false 相同，返回给父控件的onTouchEvent处理，父控件不处理再往上面传递
    如果添加了onClickListener事件，点击事件来消费
    return true 自己消费，也不会传到点击事件中

    dispatchTouchEvent：事件分发时，
     DOWN Event return true ，不再往下分发down，up事件，事件最终在OnTouchEvent的up
     DOWN Event return false ， 直接交给上层View的 OnTouchEvent事件中处理
     UP Event return true, down事件正常分发，不再往下分发up事件，up事件截止
     UP Event return false,

    onInterceptTouchEvent ：事件拦截
    DOWN Event return true   down，up事件都在本view的OnTouchEvent中处理，click可以相应
    UP Event return true   down事件不影响， up在本view的OnTouchEvent中处理，click不相应

     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch_event_layout)

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
