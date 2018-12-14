package com.zs.various.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zs.various.R;
import com.zs.various.view.TabTitleLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs
 * Date：2018年 12月 14日
 * Time：9:32
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class TabTitleActivity extends AppCompatActivity {

    private TabTitleLayout tab_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_title_layout);
        tab_view = findViewById(R.id.tab_view);

        List<String> data = new ArrayList<>();
        data.add("1128");
        data.add("1129");
        data.add("1130");

        tab_view.initData(data);
        tab_view.setItemListener(new TabTitleLayout.OnTabClickListener() {
            @Override
            public void backClick(int position) {
                Toast.makeText(TabTitleActivity.this,"positon = " + position,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
