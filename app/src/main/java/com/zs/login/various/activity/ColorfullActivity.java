package com.zs.login.various.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.zs.login.various.R;
import com.zs.login.various.bean.DataBean;

import java.text.SimpleDateFormat;
import java.util.Date;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colorfull_layout);
        ButterKnife.bind(this);

        String data = "{\"data\":{\"id\":{\"courseid\":4,\"time\":1501409165300}}}";

        DataBean bean = new Gson().fromJson(data, DataBean.class);

        DataBean.IdBean.InnerBean innerBean = bean.getData().getId();


        String date = timeStamp2Date(System.currentTimeMillis() + "");

        Log.d("My_time",System.currentTimeMillis() + "");
        Log.d("My_time",date);
    }


    public static String timeStamp2Date(String time) {

        if(TextUtils.isEmpty(time)){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(Long.valueOf(time)));
    }
}
