package com.faizzy.collapsingnestedscrollview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * Maintain nested scrolling behaviour if child is [RecyclerView] and it has focus to consume scrolling then it will communicate parent to consume
 * scroll event until parent scroll is not reached to bottom
 */
class CollapsingNestedScrollView : NestedScrollView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        val rv = target as RecyclerView
        if (dy < 0 && isRvScrolledToTop(rv) || dy > 0 && !isNsvScrolledToBottom(this)) {
            // The NestedScrollView should steal the scroll event away from the
            // RecyclerView if: (1) the user is scrolling their finger down and the
            // RecyclerView is scrolled to the top of its content, or (2) the user
            // is scrolling their finger up and the NestedScrollView is not scrolled
            // to the bottom of its content.
            scrollBy(0, dy)
            consumed[1] = dy
            return
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type)
    }

    companion object {
        /**
         * Returns true if the [NestedScrollView] is scrolled to the bottom
         * of its content (i.e. the child is completely expanded).
         */
        private fun isNsvScrolledToBottom(nsv: NestedScrollView): Boolean {
            return !nsv.canScrollVertically(1)
        }

        /**
         * Returns true if the [RecyclerView] is scrolled to the
         * top of its content (i.e. its first item is completely visible).
         */
        private fun isRvScrolledToTop(rv: RecyclerView): Boolean {
            val lm = rv.layoutManager as LinearLayoutManager?
            return (Objects.requireNonNull(lm)!!.findFirstVisibleItemPosition() == 0
                    && Objects.requireNonNull(lm!!.findViewByPosition(0))!!.top == 0)
        }
    }
}