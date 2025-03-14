package com.wkq.tools.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.RelativeLayout
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.wkq.tools.R
import com.wkq.tools.ui.adapter.VpFragmentAdapter
import kotlin.math.abs

class ViewPager2OutContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var mViewPager2: ViewPager2? = null
    private var disallowParentInterceptDownEvent = true
    private var startX = 0f
    private var startY = 0f
    private var MIN_DISTANCE = 0f

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView is ViewPager2) {
                mViewPager2 = childView
               val rv= mViewPager2?.get(0)as RecyclerView
                break
            }
        }
        if (mViewPager2 == null) {
            throw IllegalStateException(
                "The root child of ViewPager2Container must contains a ViewPager2"
            )
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val handle = super.dispatchTouchEvent(ev)
//        Log.e("滑动拦截:", "dispatchTouchEvent")
//        mViewPager2?.setCurrentItem(1,true)

        return handle
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
//        return super.onInterceptTouchEvent(ev)

        val currentItem = mViewPager2?.currentItem
        val itemCount = mViewPager2?.adapter!!.itemCount
        intercepted = false
        var isLeft=false

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x
                startY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val currentX = ev.x
                val currentY = ev.y
                val dx = currentX - startX
                val dy = currentY - startY

                // 判断是否是有效的滑动
                if (Math.abs(dx) > MIN_DISTANCE || Math.abs(dy) > MIN_DISTANCE) {
                    if (Math.abs(dx) > Math.abs(dy)) {
                        // 水平滑动
                        if (dx > 0) {
                            // 右滑
                            Log.e("滑动:","右滑")
                            isLeft=false
                        } else {
                            // 左滑
                            isLeft=true
                            Log.e("滑动:","左滑")

                        }
                    } else {
                        // 垂直滑动
                        if (dy > 0) {
                            // 下滑
                            println("下滑")
                        } else {
                            // 上滑
                            println("上滑")
                        }
                    }
                }
            }
        }

        Log.e("滑动拦截:", "currentItem:"+currentItem)
        if (currentItem == 1) {
            val vpFragmentAdapter = mViewPager2?.adapter as VpFragmentAdapter
            val fragment = vpFragmentAdapter.fragments.get(1)
            val vp = fragment.view?.findViewById<ViewPager2>(R.id.vp_content)


            val childCur = vp?.currentItem
            val childTotal = vp?.adapter!!.itemCount
            if (childCur in 1..childTotal-2 ) {
                mViewPager2!!.setUserInputEnabled(false)
                Log.e("滑动拦截:", "childCur"+childCur)
            } else {
                if (isLeft){
                    Log.e("滑动拦截:", "左边")
                    if (childCur==(childTotal-1)){

                        mViewPager2!!.setUserInputEnabled(true)
                    }else{
                        mViewPager2!!.setUserInputEnabled(false)
                    }
                }else{
                    Log.e("滑动拦截:", "右边")
                    if (childCur==0){
//                        Log.e("滑动拦截:", "onInterceptTouchEvent")
                        mViewPager2!!.setUserInputEnabled(true)
                    }else{
                        mViewPager2!!.setUserInputEnabled(false)
                    }
                }
            }


            intercepted = false

        } else {
            intercepted = false
        }
//        Thread.sleep(50)
        return super.onInterceptTouchEvent(ev)
    }


    var intercepted = false
    fun setHaveViewPager2Content(have: Boolean) {
        intercepted = have
    }
}