package com.incrediblehohol.userlist.utils.ui.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.incrediblehohol.userlist.R
import javax.inject.Inject


class SimpleSpacesItemDecoration @Inject constructor() :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)

        if (position != parent.childCount && position > 0) {
            outRect.top = view.resources.getDimension(R.dimen.margin_small).toInt()
        }
    }
}