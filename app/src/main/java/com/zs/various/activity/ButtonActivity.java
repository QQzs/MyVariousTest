package com.zs.various.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.zs.various.R;
import com.zs.various.view.AnimButton;

/**
 * Created by zs
 * Date：2017年 05月 11日
 * Time：14:28
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class ButtonActivity extends Activity {

//    @Bind(R.id.btn)
    AnimButton button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_layout);
//        ButterKnife.bind(this);
        button = findViewById(R.id.btn);

        button.setAnimationButtonListener(new AnimButton.AnimationButtonListener() {
            @Override
            public void onClickListener() {
                button.startAnim();
            }

            @Override
            public void animationFinish() {
                Toast.makeText(ButtonActivity.this,"animationFinish",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
