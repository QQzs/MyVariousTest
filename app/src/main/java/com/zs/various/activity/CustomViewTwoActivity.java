package com.zs.various.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zs.various.R;
import com.zs.various.base.BaseActivity;
import com.zs.various.view.ExpandTextView;
import com.zs.various.view.ExpandableTextView;
import com.zs.various.view.RoundImageView;
import com.zs.various.view.ShadowView;
import com.zs.various.view.palette.BitmapPalette;
import com.zs.various.view.palette.GlidePalette;

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

    private String mUrl = "https://freshmate-dev-bigbang-pub.oss-cn-beijing.aliyuncs.com/avatar/1000017/05ff9e913cd89f0b36c1ec184bd2a471.jpg";


    @Override
    public void initData() {
        RoundImageView roundImage = findViewById(R.id.round_img);

        final ShadowView shadow_img = findViewById(R.id.shadow_img);



        Glide.with(this)
                .load(mUrl)
                .listener(GlidePalette.with(mUrl)
                        .intoCallBack(new BitmapPalette.CallBack() {
                            @Override
                            public void onPaletteLoaded(@Nullable Palette palette) {
                                //specific task
                                int color = palette.getDominantColor(Color.WHITE);

                                shadow_img.setBack(color);

                                Log.d("My_Log" , "color = " + color);

                            }
                        })
                        .setGlideListener(new RequestListener<Drawable>() {
                            @Override public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Log.d("My_Log" , "NO");
                                return false;
                            }

                            @Override public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                Log.d("My_Log" , "YES");
                                return false;
                            }
                        }))
                .into(roundImage);
        roundImage.setImageResource(R.drawable.bg_girl);

    }

}
