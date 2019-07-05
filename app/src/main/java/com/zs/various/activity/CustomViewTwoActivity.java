package com.zs.various.activity;

import com.bumptech.glide.Glide;
import com.zs.various.R;
import com.zs.various.base.BaseActivity;
import com.zs.various.view.ExpandTextView;
import com.zs.various.view.ExpandableTextView;
import com.zs.various.view.RoundImageView;

/**
 * Created by zs
 * Date：2018年 12月 25日
 * Time：10:52
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class CustomViewTwoActivity extends BaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_custom_view2;
    }

    @Override
    public void initView() {

        ExpandTextView expandTextView = findViewById(R.id.tv_limit1);
        expandTextView.initText(getString(R.string.text_info));

        ExpandableTextView textView = findViewById(R.id.tv_limit2);
        textView.setOriginalText(getString(R.string.text_info));
        textView.setHasAnimation(true);
        textView.setMaxLines(2);

    }



    @Override
    public void initData() {
        RoundImageView roundImage = findViewById(R.id.round_img);
        Glide.with(this).load("https://freshmate-dev-bigbang-pub.oss-cn-beijing.aliyuncs.com/avatar/1000017/05ff9e913cd89f0b36c1ec184bd2a471.jpg").into(roundImage);
//        roundImage.setImageResource(R.drawable.bg_girl);

    }

}
