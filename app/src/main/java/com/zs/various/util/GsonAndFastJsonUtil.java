package com.zs.various.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zs.various.bean.User;

import java.util.List;

/**
 * Created by zs
 * Date：2018年 10月 29日
 * Time：16:16
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class GsonAndFastJsonUtil {

    /**
     * Gson 解析格式
     */
    public void gson(){

        User user = new Gson().fromJson("" , User.class);

        List<User> userList = new Gson().fromJson("" , new TypeToken<List<User>>(){}.getType());

    }

    /**
     * FastJSON 解析格式
     *
     * FastJSON 解析数组时，解析的Bean需要有一个空的构造方法，不然会报错
     *
     */
    public void fastJson(){

        User user = JSON.parseObject("" , User.class);

        List<User> userList = JSON.parseArray("" , User.class);

    }


}
