package com.zs.various.kotlin;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.zs.various.R;
import com.zs.various.base.BaseActivity;
import com.zs.various.kotlin.extension.KotlinExtensionKt;
import com.zs.various.kotlin.view.TestView;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/**
 * @Author: zs
 * @Date: 20/12/23 上午8:29
 * @Description:
 */
public class JavaTestActivity extends BaseActivity {

    private TextView tv;
    private ImageView iv;

    private TestView testView;

    @Override
    protected int setLayoutId() {
        return R.layout.kotlin_test_layout;
    }

    @Override
    public void initView() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData() {

        tv = findViewById(R.id.tv_age);
        iv = findViewById(R.id.iv_avatar);
        testView = findViewById(R.id.test_view);

        KotlinTestActivity.getNum4();
        KotlinTestActivity.Companion.getData();
        KotlinTestActivity.getNewData();

        int b = KotlinTestActivity.StaticData.num7;
        int a = KotlinTestActivityKt.num8;


        KotlinExtensionKt.getBack("test");
        KotlinExtensionKt.setColor(tv, R.color.color_0);
        KotlinExtensionKt.loadImage(iv, R.drawable.head_bg_img);

        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


        // java 调用Kotlin回调函数
        testView.setCallBack(new Function2<String, Integer, Unit>() {

            @Override
            public Unit invoke(String s, Integer integer) {

                return null;
            }
        });

    }
}
