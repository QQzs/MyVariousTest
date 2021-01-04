package com.zs.various.kotlin;

import android.media.Image;
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
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        KotlinTestActivity.Companion.getNum4();

        KotlinTestActivity.getNewData();

        int b = KotlinTestActivity.StaticData.num7;

        int a = KotlinTestActivityKt.num8;


        KotlinExtensionKt.getBack("test");
        KotlinExtensionKt.setColor(tv, R.color.color_0);
        KotlinExtensionKt.loadImage(iv, R.drawable.head_bg_img);
    }
}
