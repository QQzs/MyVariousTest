package com.zs.various.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.zs.various.R
import com.zs.various.bean.SortModel
import com.zs.various.util.CharacterParser
import com.zs.various.util.PinyinComparator
import kotlinx.android.synthetic.main.contact_list_layout.view.*
import java.util.*

/**
 * Created by zs
 * Date：2018年 07月 09日
 * Time：13:36
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class ContactRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) , SideBarView.LetterTouchListener{

    var mData: MutableList<SortModel>? = null
    var mParser: CharacterParser = CharacterParser.getInstance()

    init {
        LayoutInflater.from(context).inflate(R.layout.contact_list_layout, this)
        view_sidebar?.setLetterTouchListener(this)
    }

    fun getRecycler(): RecyclerView{
        return recycler_view
    }

    override fun setLetterVisibility(visibility: Int) {
        tv_letter?.visibility = visibility
    }

    override fun setLetter(letter: String?) {
        if (TextUtils.isEmpty(letter) || letter!!.isEmpty()){
            return
        }
        tv_letter?.text = letter
        var position = getPositionForSection(letter!![0].toInt())
        if (position != -1) {
            (recycler_view?.layoutManager as LinearLayoutManager)?.scrollToPositionWithOffset(position,0) // 使当前位置处于最顶端
        }
    }

    // 数据排序
    fun sortData(data: MutableList<String>): MutableList<SortModel> {
        val list = mutableListOf<SortModel>()
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
        Collections.sort(list, PinyinComparator())
        mData = list
        return list
    }

    // 根据输入的内容刷新数据
    fun updateData(filterStr: String): MutableList<SortModel> {
        var newData = mutableListOf<SortModel>()
        if (mData != null && mData!!.size > 0){
            if ("" == filterStr) {
                newData = mData!!
            } else {
                for (sortModel in mData!!) {
                    val name = sortModel.name
                    if (name.indexOf(filterStr) != -1 || mParser.getSelling(name).startsWith(filterStr)) {
                        newData.add(sortModel)
                    }
                }
            }
        }
        return newData
    }

    // 获取字母首次出现的位置
    fun getPositionForSection(section: Int): Int {
        if (mData == null || mData!!.size == 0){
            return -1
        }
        for (i in 0 until mData!!.size) {
            val s = mData!![i].letter
            val firstChar = s.toUpperCase()[0]
            if (firstChar.toInt() == section) {
                return i
            }
        }
        return -1
    }

}
