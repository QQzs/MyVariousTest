package com.zs.various.util.extension

import androidx.core.content.ContextCompat
import android.widget.TextView
import org.jetbrains.anko.dip

/**
 * @Author: zs
 * @Date: 2019-07-22 10:48
 *
 * @Description: TextView Drawable
 */

const val DRAW_LEFT = "left"
const val DRAW_RIGHT = "right"

/**
 * draw left
 */
fun TextView.drawLeft(drawableId: Int?){
    drawImage(DRAW_LEFT , drawableId)
}
fun TextView.drawLeft(drawableId: Int? , padding: Int){
    drawLeft(drawableId)
    if (drawableId != null){
        this.compoundDrawablePadding = dip(padding)
    }
}

/**
 * draw right
 */
fun TextView.drawRight(drawableId: Int?){
    drawImage(DRAW_RIGHT , drawableId)
}
fun TextView.drawRight(drawableId: Int? , padding: Int){
    drawRight(drawableId)
    if (drawableId != null){
        this.compoundDrawablePadding = dip(padding)
    }
}


fun TextView.drawImage(draw: String , drawableId: Int?){

    if(drawableId == null){
        this.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    }else{
        when(draw){
            DRAW_LEFT ->{
                this.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this.context , drawableId), null, null, null)
            }
            DRAW_RIGHT ->{
                this.setCompoundDrawablesWithIntrinsicBounds(null,  null, ContextCompat.getDrawable(this.context , drawableId), null)
            }
        }
    }


}