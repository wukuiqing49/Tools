package com.wkq.tools.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.RelativeLayout
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import android.view.VelocityTracker
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView

class ViewPager2GroupContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var mViewPager2: ViewPager2? = null
    private var hasViewPager2Content: Boolean = false

    fun setHaveViewPager2Content(have: Boolean) {
        hasViewPager2Content = have
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView is ViewPager2) {
                mViewPager2 = childView
                break
            }
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

       val handle= super.dispatchTouchEvent(ev)
        Log.e("拦截dispatchTouchE:", "是否有:" + hasViewPager2Content + "  " + handle)
//        super.dispatchTouchEvent(ev)
//        if (hasViewPager2Content)return false
        return handle
    }

    private var startX = 0
    private var startY = 0
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.e("拦截:", "是否有:" + hasViewPager2Content + "  " + this.getTag())

        val doNotNeedIntercept = (!mViewPager2!!.isUserInputEnabled
                || (mViewPager2?.adapter != null
                && mViewPager2?.adapter!!.itemCount <= 1))
        if (doNotNeedIntercept) {
            return super.onInterceptTouchEvent(ev)
        }

        if (hasViewPager2Content) {
//            mViewPager2!!.setUserInputEnabled(false)

            ( mViewPager2!!.get(0) as RecyclerView).setOnTouchListener(object :OnTouchListener{
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                    return false
                }
            })
            return false

        } else {

            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = ev.x.toInt()
                    startY = ev.y.toInt()

                }

                MotionEvent.ACTION_MOVE -> {
                    val endX = ev.x.toInt()
                    val endY = ev.y.toInt()
                    val disX = abs(endX - startX)
                    val disY = abs(endY - startY)
                    if (mViewPager2!!.orientation == ViewPager2.ORIENTATION_VERTICAL) {
                        onVerticalActionMove(endY, disX, disY)
                    } else if (mViewPager2!!.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                        onHorizontalActionMove(endX, disX, disY)
                    }
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(
                    false
                )
            }

        }
        return super.onInterceptTouchEvent(ev)
    }

    private fun onHorizontalActionMove(endX: Int, disX: Int, disY: Int) {
        if (mViewPager2?.adapter == null) {
            return
        }
        if (disX > disY) {
            val currentItem = mViewPager2?.currentItem
            val itemCount = mViewPager2?.adapter!!.itemCount
            if (currentItem == 0 && endX - startX > 0) {
                parent.requestDisallowInterceptTouchEvent(false)
            } else {
                parent.requestDisallowInterceptTouchEvent(
                    currentItem != itemCount - 1
                            || endX - startX >= 0
                )
            }
        } else if (disY > disX) {
            parent.requestDisallowInterceptTouchEvent(false)
        }
    }

    private fun onVerticalActionMove(endY: Int, disX: Int, disY: Int) {
        if (mViewPager2?.adapter == null) {
            return
        }
        val currentItem = mViewPager2?.currentItem
        val itemCount = mViewPager2?.adapter!!.itemCount
        if (disY > disX) {
            if (currentItem == 0 && endY - startY > 0) {
                parent.requestDisallowInterceptTouchEvent(false)
            } else {
                parent.requestDisallowInterceptTouchEvent(
                    currentItem != itemCount - 1
                            || endY - startY >= 0
                )
            }
        } else if (disX > disY) {
            parent.requestDisallowInterceptTouchEvent(false)
        }
    }

}