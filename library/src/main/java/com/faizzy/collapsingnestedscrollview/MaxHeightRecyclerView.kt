package com.faizzy.collapsingnestedscrollview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * A [RecyclerView] with an optional maximum height.
 */
class MaxHeightRecyclerView : RecyclerView {
    private var mMaxHeight = ViewGroup.LayoutParams.WRAP_CONTENT

    constructor(context: Context) : super(context) {
        initialize(context,null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context,attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context,attrs)
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        var heightSpec = heightSpec
        val mode = MeasureSpec.getMode(heightSpec)
        val height = MeasureSpec.getSize(heightSpec)
        if (mMaxHeight >= 0 && (mode == MeasureSpec.UNSPECIFIED || height > mMaxHeight)) {
            heightSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST)
        }
        super.onMeasure(widthSpec, heightSpec)
    }
    private fun initialize(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val arr = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView)
            mMaxHeight = arr.getLayoutDimension(
                R.styleable.MaxHeightRecyclerView_maxHeight,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            arr.recycle()
        }

    }


    /**
     * Sets the maximum height for this recycler view.
     */
    fun setMaxHeight(maxHeight: Int) {
        if (mMaxHeight != maxHeight) {
            mMaxHeight = maxHeight
            requestLayout()
        }
    }
}