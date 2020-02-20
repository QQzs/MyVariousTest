package com.zs.various.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.zs.various.R
import com.zs.various.adapter.ContactSortAdapter
import com.zs.various.bean.SortModel
import com.zs.various.util.CharacterParser
import com.zs.various.util.PinyinComparator
import com.zs.various.util.RecyclerViewUtil
import com.zs.various.view.SideBarView
import kotlinx.android.synthetic.main.activity_sort_list.*
import java.util.*

/**
 *
Created by zs
Date：2018年 07月 23日
Time：15:04
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ContactActivity: AppCompatActivity() , SideBarView.LetterTouchListener{

    private var mAdater: ContactSortAdapter? = null
    private var mDataList: MutableList<SortModel>? = null
    private var mComparator: PinyinComparator = PinyinComparator()
    private var mParser: CharacterParser = CharacterParser.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort_list)
        initView()
        initData()
    }

    fun initView(){
        sidebar.setLetterTouchListener(this)
        search.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                // TODO Auto-generated method stub
                Log.i("TAG", "onTextChanged")
                filterData(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                // TODO Auto-generated method stub

            }

            override fun afterTextChanged(arg0: Editable) {
                // TODO Auto-generated method stub

            }
        })

    }

    fun initData(){

        val arrayData = arrayOf("a", "bd", "ced", "de", "as", "许爱明", "秋雅", "夏洛", "马冬梅", "大春", "袁华", "我", "你", "啊", "哈哈", "嘿")
        var data = mutableListOf<String>()
        for (i in arrayData){
            data.add(i)
        }
        mDataList = filledData(data)
        Collections.sort(mDataList, mComparator) // 排序
        mAdater = ContactSortAdapter()
        RecyclerViewUtil.init(this,recycler_view,mAdater)
        mAdater?.initData(mDataList)

    }

    // 为list填充数据
    private fun filledData(data: MutableList<String>): ArrayList<SortModel> {
        val list = ArrayList<SortModel>()
        for (i in data.indices) {
            val sm = SortModel()
            sm.name = data[i]
            val pinyin = mParser.getSelling(data[i])
            val sortString = pinyin.substring(0, 1).toUpperCase()
            if (sortString.matches("[A-Z]".toRegex())) {
                sm.letter = sortString
            } else {
                sm.letter = "#"
            }
            list.add(sm)
        }
        return list
    }

    // 根据输入的内容刷新listview的数据
    private fun filterData(filterStr: String) {
        // TODO Auto-generated method stub
        if (mDataList == null || mDataList!!.size == 0){
            return
        }
        var filterData = mutableListOf<SortModel>()
        if ("" == filterStr) {
            filterData = mDataList!!
        } else {
            filterData.clear()
            for (sortModel in mDataList!!) {
                val name = sortModel.name
                if (name.indexOf(filterStr) != -1 || mParser.getSelling(name).startsWith(filterStr)) {
                    filterData.add(sortModel)
                }
            }
        }
        mAdater?.initData(filterData)
    }

    override fun setLetterVisibility(visibility: Int) {
        tv_letter_show?.visibility = visibility
    }

    override fun setLetter(letter: String?) {
        if (mAdater == null || letter == null){
           return
        }
        tv_letter_show?.text = letter
        var position = mAdater!!.getPositionForSection(letter!![0].toInt())
        if (position != -1) {
            (recycler_view?.layoutManager as androidx.recyclerview.widget.LinearLayoutManager)?.scrollToPositionWithOffset(position,0) // 使当前位置处于最顶端
        }
    }

}