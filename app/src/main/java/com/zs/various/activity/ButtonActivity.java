package com.zs.various.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class ButtonActivity extends AppCompatActivity {

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
