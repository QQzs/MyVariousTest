package com.zs.various.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;

import com.zs.various.R;
import com.zs.various.util.Constant;
import com.zs.various.util.extension.ImageViewExtensionKt;

/**
 * Created by zs
 * Date：2017年 05月 11日
 * Time：14:28
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class ColorfullActivity extends Activity {

    private ImageView iv_show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colorfull_layout);
        iv_show = findViewById(R.id.iv_show);

//        Picasso.with(this)
//                .load(Constant.IMAGE_URL)
//                .placeholder(R.mipmap.timg)
//                .into(iv_show);

        ImageViewExtensionKt.loadImage(iv_show,Constant.IMAGE_URL);

        iv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ColorfullActivity.this,ColorfullBackActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        ColorfullActivity.this,
                        iv_show,
                        getString(R.string.transition_image)
                );
                ActivityCompat.startActivity(ColorfullActivity.this, intent, optionsCompat.toBundle());
            }
        });


    }
}
