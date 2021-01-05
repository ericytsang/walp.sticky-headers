package com.github.ericytsang.foregroundnotification.app.stickyheader

/*
solution based on - based on Sevastyan answer on StackOverflow
changes:
- take to account views offsets
- transformed to Kotlin
- now works on viewHolders
- try to cache viewHolders between draw's
- support for clipToPadding=false
Source:
https://stackoverflow.com/questions/32949971/how-can-i-make-sticky-headers-in-recyclerview-without-external-lib/44327350#44327350
*/

// todo make sticky headers clickable
// todo sticky headers don't change colors when they are stuck to the top


import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HeaderItemDecoration(
    parent: RecyclerView,
    private val shouldFadeOutHeader: Boolean = false,
    private val isHeader: (itemPosition: Int) -> Boolean
) : RecyclerView.ItemDecoration() {

    private var currentHeader: Pair<Int, RecyclerView.ViewHolder>? = null

    init {
        parent.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                // clear saved header as it can be outdated now
                currentHeader = null
            }
        })

        parent.doOnEachNextLayout {
            // clear saved layout as it may need layout update
            currentHeader = null
        }

        // handle click on sticky header
        parent.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(
                recyclerView: RecyclerView,
                motionEvent: MotionEvent
            ): Boolean {
                return if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    motionEvent.y <= currentHeader?.second?.itemView?.bottom ?: 0
                } else false
            }
        })
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        //val topChild = parent.getChildAt(0) ?: return
        val topChild = parent.findChildViewUnder(
            parent.paddingLeft.toFloat(),
            parent.paddingTop.toFloat() /*+ (currentHeader?.second?.itemView?.height ?: 0 )*/
        ) ?: return
        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }

        val headerView = getHeaderViewForItem(topChildPosition, parent) ?: return

        val contactPoint = headerView.bottom + parent.paddingTop
        getNextInContactHeader(parent, contactPoint)?.let { headerInContact ->
            moveHeader(c, headerView, headerInContact, parent.paddingTop)
        } ?: run {
            drawHeader(c, headerView, parent.paddingTop)
        }
    }

    private fun getHeaderViewForItem(itemPosition: Int, parent: RecyclerView): View? {
        if (parent.adapter == null) {
            return null
        }
        val headerPosition = getHeaderPositionForItem(itemPosition)
        if (headerPosition == RecyclerView.NO_POSITION) return null
        val headerType = parent.adapter?.getItemViewType(headerPosition) ?: return null
        // if match reuse viewHolder
        if (currentHeader?.first == headerPosition && currentHeader?.second?.itemViewType == headerType) {
            return currentHeader?.second?.itemView
        }

        val headerHolder = parent.adapter?.createViewHolder(parent, headerType)
        if (headerHolder != null) {
            parent.adapter?.onBindViewHolder(headerHolder, headerPosition)
            fixLayoutSize(parent, headerHolder.itemView)
            // save for next draw
            currentHeader = headerPosition to headerHolder
        }
        return headerHolder?.itemView
    }

    private fun drawHeader(c: Canvas, header: View, paddingTop: Int) {
        c.save()
        c.translate(0f, paddingTop.toFloat())
        header.draw(c)
        c.restore()
    }

    private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View, paddingTop: Int) {
        c.save()
        if (!shouldFadeOutHeader) {
            c.clipRect(0, paddingTop, c.width, paddingTop + currentHeader.height)
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                c.saveLayerAlpha(
                    RectF(0f, 0f, c.width.toFloat(), c.height.toFloat()),
                    (((nextHeader.top - paddingTop) / nextHeader.height.toFloat()) * 255).toInt()
                )
            } else {
                c.saveLayerAlpha(
                    0f, 0f, c.width.toFloat(), c.height.toFloat(),
                    (((nextHeader.top - paddingTop) / nextHeader.height.toFloat()) * 255).toInt(),
                    Canvas.ALL_SAVE_FLAG
                )
            }

        }
        c.translate(0f, (nextHeader.top - currentHeader.height).toFloat() /*+ paddingTop*/)

        currentHeader.draw(c)
        if (shouldFadeOutHeader) {
            c.restore()
        }
        c.restore()
    }

    private fun getNextInContactHeader(parent: RecyclerView, contactPoint: Int): View? {
        return (0 until parent.childCount)
            .asSequence()
            .map { parent.getChildAt(it) }
            .filter { child -> isHeader(parent.getChildAdapterPosition(child)) }
            .firstOrNull { child ->
                val childBounds = Rect()
                parent.getDecoratedBoundsWithMargins(child, childBounds)
                childBounds.top in 1..contactPoint
            }
    }

    /**
     * Properly measures and layouts the top sticky header.
     *
     * @param parent ViewGroup: RecyclerView in this case.
     */
    private fun fixLayoutSize(parent: ViewGroup, view: View) {

        // Specs for parent (RecyclerView)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec =
            View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            view.layoutParams.width
        )
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )

        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    private fun getHeaderPositionForItem(itemPosition: Int): Int {
        return (itemPosition downTo 0).find(isHeader) ?: RecyclerView.NO_POSITION
    }
}

inline fun View.doOnEachNextLayout(crossinline action: (view: View) -> Unit) {
    addOnLayoutChangeListener { view, _, _, _, _, _, _, _, _ ->
        action(
            view
        )
    }
}