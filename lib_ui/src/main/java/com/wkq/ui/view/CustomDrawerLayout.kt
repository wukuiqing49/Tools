package com.wkq.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView


/**
 *
 *@Author: wkq
 *
 *@Time: 2025/1/21 11:07
 *
 *@Desc:  通过 xy 判断点击位置是否在 禁止拦截的View内 在范围内 禁止拦截  不在范围内 父类方法处理
 */
class CustomDrawerLayout : DrawerLayout {
    //禁止拦截的ID
    val limitIds = HashSet<Int>()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    /**
     *
     * 重写事件拦截方法
     * @param event MotionEvent
     * @return Boolean
     */
    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            //方向确定设置为false
            val x = event.rawX.toInt()
            val y = event.rawY.toInt()
            //通过x,y 判断是否禁止拦截  禁止拦截 返回false
            if (isLimitIntercept(x, y)) return false
        }
        return super.onInterceptTouchEvent(event)
    }

    /**
     * 判断 是否禁止拦截
     * @param x Int
     * @param y Int
     * @return Boolean
     */
    private fun isLimitIntercept(x: Int, y: Int): Boolean {
        limitIds.forEach {
            if (isTouchPointInView(findViewById<View>(it), x, y)) {
                if (findViewById<View>(it) is RecyclerView) {
                    // 是否 可以向左滑动
                    val isHorizontally = (findViewById<View>(it) as RecyclerView).canScrollHorizontally(-1)
                    if (!isHorizontally) {
                        return false
                    } else {
                        //当前触摸的位置 是否属于此View
                        return true
                    }
                } else {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 添加禁止拦截的id
     * @param id Int
     */
    fun addLimitIntercept(id: Int) {
        limitIds.add(id)
    }

    /**
     * 点击区域 是否在当前 View 的区域内
     * @param view View?
     * @param x Int
     * @param y Int
     * @return Boolean
     */
    fun isTouchPointInView(view: View?, x: Int, y: Int): Boolean {
        if (view == null || VISIBLE != view.visibility) {
            return false
        }
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val left = location[0]
        val top = location[1]
        val right = left + view.measuredWidth
        val bottom = top + view.measuredHeight
        if (y >= top && y <= bottom && x >= left && x <= right) {
            return true
        }
        return false
    }

}