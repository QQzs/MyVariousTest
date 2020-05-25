package com.zs.various.adapter

import androidx.recyclerview.widget.RecyclerView
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper
import com.zs.various.R
import com.zs.various.bean.User

/**
 * @Author: zs
 * @Date: 2020-05-06 17:18
 *
 * @Description:
 */
class RvAdapter(recyclerView: RecyclerView?) : BGARecyclerViewAdapter<User>(recyclerView , R.layout.item_view_layout) {

    override fun fillData(helper: BGAViewHolderHelper?, position: Int, model: User?) {
        helper?.setText(R.id.tv_item_title , model?.name)

    }

}