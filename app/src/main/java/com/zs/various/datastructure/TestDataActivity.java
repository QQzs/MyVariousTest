package com.zs.various.datastructure;

import android.view.View;
import android.widget.Button;

import com.zs.various.R;
import com.zs.various.base.BaseActivity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: zhangshuai
 * @CreateDate: 2020/7/20 2:51 PM
 * @Description:
 */
public class TestDataActivity extends BaseActivity implements View.OnClickListener {

    private int key = 0;
    private Button btn_put;
    private HashTable<Integer , String> table;
    private LinkedHashMap<String , String> linkedHashMap;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_test_data;
    }

    @Override
    public void initView() {
        btn_put = findViewById(R.id.btn_put);
        btn_put.setOnClickListener(this);
    }

    @Override
    public void initData() {
        table = new HashTable<>();

        linkedHashMap = new LinkedHashMap<>(16 , 0.75f , true);
        linkedHashMap.put("a" , "a");
        linkedHashMap.put("b" , "b");
        linkedHashMap.put("c" , "c");
        linkedHashMap.put("d" , "d");
        linkedHashMap.put("f" , "f");
        linkedHashMap.put("e" , "e");

        linkedHashMap.get("e");
        linkedHashMap.put("f" , "ff");

        for(Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
            System.out.println("key:" + entry.getKey() + "   value:" + entry.getValue());
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_put:
                int k = key % 4;
                table.put(k , "value:" + k);
                key++;
                break;
        }
    }
}
