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
    private LinkedHashMap<Integer , String> linkedHashMap1;
    private LinkedHashMap<Integer , String> linkedHashMap2;

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

        // 按插入顺序
        linkedHashMap1 = new LinkedHashMap<>(16 , 0.75f , true);
        linkedHashMap1.put(1 , "a");
        linkedHashMap1.put(2 , "b");
        linkedHashMap1.put(3 , "c");
        linkedHashMap1.put(5 , "e");
        linkedHashMap1.put(4 , "d");
        linkedHashMap1.get(4);
        linkedHashMap1.get(5);

        System.out.println("=================== 按插入顺序 ==============================");
        for(Map.Entry<Integer, String> entry : linkedHashMap1.entrySet()) {
            System.out.println("key:" + entry.getKey() + "   value:" + entry.getValue());
        }

        // 按访问顺序
        linkedHashMap2 = new LinkedHashMap<>(16 , 0.75f , true);
        linkedHashMap2.put(1 , "a");
        linkedHashMap2.put(2 , "b");
        linkedHashMap2.put(3 , "c");
        linkedHashMap2.put(5 , "e");
        linkedHashMap2.put(4 , "d");
        linkedHashMap2.get(4);
        linkedHashMap2.get(5);

        System.out.println("================== 按访问顺序 ===========================");
        for(Map.Entry<Integer, String> entry : linkedHashMap2.entrySet()) {
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
