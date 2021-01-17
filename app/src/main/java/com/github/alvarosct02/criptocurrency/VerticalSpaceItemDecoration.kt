package com.github.alvarosct02.criptocurrency

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class VerticalSpaceItemDecoration(
    private val verticalSpaceHeight: Int
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemCount = parent.adapter?.itemCount ?: 0

//        First
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = verticalSpaceHeight
        }

//        In-Between
        if (parent.getChildAdapterPosition(view) != itemCount - 1) {
            outRect.bottom = verticalSpaceHeight
        }

//        Last
        if (parent.getChildAdapterPosition(view) == itemCount - 1) {
            outRect.bottom = verticalSpaceHeight
        }

//        Sides
        outRect.right = verticalSpaceHeight
        outRect.left = verticalSpaceHeight

    }
}