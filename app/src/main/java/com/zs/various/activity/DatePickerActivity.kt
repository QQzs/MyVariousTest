package com.zs.various.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.zs.various.R
import com.zs.various.view.datepicker.DatePickerPopWin
import kotlinx.android.synthetic.main.activity_date_picker.*

/**
 *
Created by zs
Date：2018年 04月 04日
Time：14:00
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class DatePickerActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_picker)

    }

    fun datePicker(view: View){

        val pickerPopWin = DatePickerPopWin.Builder(this, DatePickerPopWin.OnDatePickedListener {
            year, month, day, dateDesc -> tv_date?.text = dateDesc
        })
                .textConfirm("完成") //text of confirm button
                .textCancel("取消") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(18) // pick view text size
                .colorCancel(Color.parseColor("#222222")) //color of cancel button
                .colorConfirm(Color.parseColor("#222222"))//color of confirm button
                .minYear(1900) //min year in loop
                .maxYear(2550) // max year in loop
                .showDayMonthYear(false) // shows like dd mm yyyy (default is false)
                .dateChose(tv_date?.text.toString()) // date chose when init popwindow
                .build()

        pickerPopWin.showPopWin(this)

    }

}
