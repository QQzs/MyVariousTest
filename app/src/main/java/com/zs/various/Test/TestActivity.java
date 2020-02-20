package com.zs.various.Test;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by zs
 * Date：2019年 01月 22日
 * Time：15:57
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        HashMap<String , String> map = new HashMap<>();
        for (Map.Entry<String , String> entry : map.entrySet()) {

        }

        List<String> array = new ArrayList<>();

        Luban.with(this)
                .load(array)
                .ignoreBy(100)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                })
                .launch();


    }
}
