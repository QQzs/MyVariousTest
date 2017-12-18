package com.zs.various.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zs.various.R;
import com.zs.various.util.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zs
 * Date：2017年 05月 11日
 * Time：14:28
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class ColorfullActivity extends Activity {


    @Bind(R.id.iv_show)
    ImageView iv_show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colorfull_layout);
        ButterKnife.bind(this);

        Picasso.with(this)
                .load(Constant.IMAGE_URL)
                .placeholder(R.mipmap.timg)
                .into(iv_show);

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
//                startActivity(intent);
            }
        });


    }
}
