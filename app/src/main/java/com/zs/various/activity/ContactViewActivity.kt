package com.zs.various.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.zs.various.R
import com.zs.various.adapter.ContactSortAdapter
import com.zs.various.bean.SortModel
import com.zs.various.util.RecyclerViewUtil
import kotlinx.android.synthetic.main.activity_sort_recycler.*

/**
 *
Created by zs
Date：2018年 07月 23日
Time：15:04
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ContactViewActivity: AppCompatActivity(){

    private var mAdater: ContactSortAdapter? = null
    private var mDataList: MutableList<SortModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort_recycler)
        initView()
        initData()
    }

    fun initView(){
        ed_search?.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                if (TextUtils.isEmpty(s.toString().trim())){
                    contact_view?.initData(mDataList)
                    mAdater?.initData(mDataList)
                }else{
                    mAdater?.initData(contact_view?.updateData(s.toString()))
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {

            }

            override fun afterTextChanged(arg0: Editable) {

            }
        })

    }

    fun initData(){
        val arrayData = arrayOf("a", "bd", "ced", "de", "as", "东皇太一","宫本武藏","王昭君","李元芳","刘禅","后裔","许爱明","无名","流海"
                ,"亚瑟","貂蝉", "秋雅", "夏洛", "马冬梅", "大春", "袁华", "我", "你", "啊", "哈哈", "嘿"
                ,"无名","流海","亚瑟","貂蝉", "秋雅", "夏洛", "马冬梅", "大春", "袁华","无名","流海","亚瑟","貂蝉", "秋雅", "夏洛", "马冬梅", "大春", "袁华"
                ,"无名","流海","亚瑟","貂蝉", "秋雅", "夏洛", "马冬梅", "大春", "袁华","无名","流海","亚瑟","貂蝉", "秋雅", "夏洛", "马冬梅", "大春", "袁华")
        var data = mutableListOf<String>()
        for (i in arrayData){
            data.add(i)
        }
        mAdater = ContactSortAdapter()
        RecyclerViewUtil.init(this,contact_view?.getRecycler(),mAdater)
        mDataList = contact_view?.sortData(data)
        contact_view?.initData(mDataList)
        mAdater?.initData(mDataList)

    }

}