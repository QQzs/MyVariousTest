package com.zs.various.kotlin;

import android.annotation.SuppressLint;
import android.media.Image;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zs.various.R;
import com.zs.various.base.BaseActivity;
import com.zs.various.kotlin.extension.KotlinExtensionKt;

/**
 * @Author: zs
 * @Date: 20/12/23 上午8:29
 * @Description:
 */
public class JavaTestActivity extends BaseActivity {

    private TextView tv;
    private ImageView iv;

    @Override
    protected int setLayoutId() {
        return R.layout.kotlin_test_layout;
    }

    @Override
    public void initView() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData() {

        tv = findViewById(R.id.tv_age);
        iv = findViewById(R.id.iv_avatar);

        KotlinTestActivity.Companion.getNum4();

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

    }
}
