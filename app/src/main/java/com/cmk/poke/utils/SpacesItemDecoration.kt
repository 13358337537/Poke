package com.cmk.poke.utils

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * RecycleView ItemView 装饰工具 可以影响 itemView 的 measurement 和 draw
 * 例如:padding margin 分割线
 */
class SpacesItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) != 0){
            outRect.left = 32
        }
    }
    //可以实现类似绘制背景的效果，内容在上面
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }
    //可以绘制在内容的上面，覆盖内容
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }
}