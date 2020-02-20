package com.zs.various.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zs.various.R;

/**
 * Created by zs
 * Date：2018年 09月 28日
 * Time：10:30
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class SortActivity extends AppCompatActivity {

    private TextView tv_bubble;
    private TextView tv_quick;
    private int[] array1 = {23,4,6,1,89,33,2,44,90,3,333,12};
    private int[] array2 = {12,20,5,16,15,1,45,30};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_layout);
        tv_bubble = findViewById(R.id.tv_bubble);
        tv_quick = findViewById(R.id.tv_quick);


        tv_bubble.setText(array2String(array1));
        tv_quick.setText(array2String(array2));

    }

    public void bubble(View view){
        bubbleSort(array1);
    }

    public void quick(View view){
        quickSort(array2,0,array2.length -1);
    }

    /**
     * 冒泡排序
     * @param array
     */
    private void bubbleSort(int[] array) {
        for(int i = 0 ; i < array.length; i ++ ){
            for(int j = 0 ; j < array.length -1 - i ; j++){
                if (array[j] > array[j + 1]){
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
            tv_bubble.setText(array2String(array));
        }
    }

    /**
     * 快速排序
     * @param array
     * @param left
     * @param right
     */
    private void quickSort(int[] array , int left , int right){

        int start = left;
        int end = right;
        int key = array[start];
        while(end > start){
            while(end > start && array[end] >= key) end --;
            if (array[end] <= key){
                int temp = array[end];
                array[end] = array[start];
                array[start] = temp;
                Log.d("My_Log",array2String(array));
            }
            while(end > start && array[start] <= key) start ++;
            if (array[start] >= key){
                int temp = array[start];
                array[start] = array[end];
                array[end] = temp;
                Log.d("My_Log",array2String(array));
            }
        }

        if (start > left) quickSort(array , left , start -1);
        if (end < right) quickSort(array , end + 1 , right);
        tv_quick.setText(array2String(array));
    }

    private String array2String(int[] array){

        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < array.length; i ++ ){
            sb.append(array[i] + " ");
        }
        return sb.toString();
    }

}
