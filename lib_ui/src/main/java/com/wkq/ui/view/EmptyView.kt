package com.wkq.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.wkq.ui.databinding.LayoutCustomEmptyBinding


/**
 *@Desc:  通用空布局
 *
 *  支持类型:
 *      文+图/Button
 *      文+文+图/Button
 *      文+文+图/Button+文
 *      图/Button+文
 *
 *  支持属性:
 *     设置文字颜色
 *     设置文字大小
 *     设置图点击事件
 *     设置点击的是图 或者文字
 *
 * setStyle() 是全量方法 (排除了设置边距)
 *
 *@Author: wkq
 *
 *@Time: 2024/9/12 10:04
 *
 */
class EmptyView : RelativeLayout {

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    constructor(context: Context?, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context, attributeSet, defStyleAttr
    ) {
        initView()
    }

    private val mContext = context
    private var mTitle: CharSequence? = null
    private var mTopDesc: CharSequence? = null
    private var mBottomDesc: CharSequence? = null
    private var mTitleColor: Int? = null

    private var mBottomDescColor: Int? = null
    private var mTopDescColor: Int? = null

    private var mClickText: CharSequence? = null
    private var backgroundRes: Int? = null
    private var clickTextColor: Int? = null
    private var mEmptyTitleTop: Int? = null
    private var mEmptyTopDescTop: Int? = null
    private var mClickTextTop: Int? = null
    private var mBottomDescTop: Int? = null

    private var mTitleTextSize: Float? = null
    private var mClickTextSize: Float? = null
    private var mBottomTextSize: Float? = null
    private var mTopDescTextSize: Float? = null

    private  val binding =
        LayoutCustomEmptyBinding.inflate(LayoutInflater.from(mContext))

    interface OnEmptyClickListener {
        fun onEmptyClick(view: View)
    }

   private var listener: OnEmptyClickListener? = null

    /**
     * 按钮点击事件
     * @param listener OnEmptyClickListener
     */
    fun setOnEmptyClickListener(listener: OnEmptyClickListener) {
        this.listener = listener
    }


    /**
     * 构建者创建的时候 设置属性
     */
    private fun initView() {
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        addView(binding.root, layoutParams)
        //处理剧中
        val rootLayoutParams = binding.root.layoutParams as RelativeLayout.LayoutParams
        rootLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        binding.root.layoutParams = rootLayoutParams
        binding.mClickText.setOnClickListener {
            if (listener != null) listener!!.onEmptyClick(it)
        }
    }

    private fun setTitle(mTitle: CharSequence) {
        this.mTitle = mTitle
        if (!mTitle.isNullOrEmpty()) {
            binding.mEmptyTitle.text = mTitle
            binding.mEmptyTitle.visibility = VISIBLE
        } else {
            binding.mEmptyTitle.visibility = GONE
        }
    }

    private fun setTitleColor(mTitleColor: Int) {
        this.mTitleColor = mTitleColor
        if (mTitleColor > 0) {
            binding.mEmptyTitle.setTextColor(ContextCompat.getColor(mContext, mTitleColor))
        }
    }

    private fun setTitleTextSize(mTitleTextSize: Float) {
        this.mTitleTextSize = mTitleTextSize
        if (mTitleTextSize > 0) {
            binding.mEmptyTitle.setTextSize(mTitleTextSize)
        }
    }


    private fun setClickTextSize(mClickTextSize: Float) {
        this.mClickTextSize = mClickTextSize
        if (mClickTextSize > 0) {
            binding.mClickText.setTextSize(mClickTextSize)
        }
    }

    private fun setEmptyBottomTextSize(mBottomTextSize: Float) {
        this.mBottomTextSize = mBottomTextSize
        if (mBottomTextSize > 0) {
            binding.mEmptyBottomDesc.setTextSize(mBottomTextSize)
        }
    }

    private fun setEmptyTopDescTextSize(mTopDescTextSize: Float) {
        this.mTopDescTextSize = mTopDescTextSize
        if (mTopDescTextSize > 0) {
            binding.mEmptyTopDesc.setTextSize(mTopDescTextSize)
        }
    }

    private fun setTitleTop(mEmptyTitleTop: Int) {
        this.mEmptyTitleTop = mEmptyTitleTop
        if (mEmptyTitleTop >= 0) {
            val layoutParams = binding.mEmptyTitle.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.topMargin = mEmptyTitleTop
        }
    }

    private fun setEmptyTopDesc(mTopDesc: CharSequence) {
        this.mTopDesc = mTopDesc
        if (!mTopDesc.isNullOrEmpty()) {
            binding.mEmptyTopDesc.text = mTopDesc
            binding.mEmptyTopDesc.visibility = VISIBLE
        } else {
            binding.mEmptyTopDesc.visibility = GONE
        }
    }


    private fun setEmptyTopDescColor(mTopDescColor: Int) {
        this.mTopDescColor = mTopDescColor
        if (mTopDescColor > 0) {
            binding.mEmptyTopDesc.setTextColor(ContextCompat.getColor(mContext, mTopDescColor))
        }
    }

    private fun setEmptyBottomDescColor(mBottomDescColor: Int) {
        this.mBottomDescColor = mBottomDescColor
        if (mBottomDescColor > 0) {
            binding.mEmptyBottomDesc.setTextColor(
                ContextCompat.getColor(
                    mContext, mBottomDescColor
                )
            )
        }
    }

    private fun setEmptyTopDescTop(mEmptyTopDescTop: Int) {
        this.mEmptyTopDescTop = mEmptyTopDescTop
        if (mEmptyTopDescTop >= 0) {
            val layoutParams = binding.mEmptyTopDesc.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.topMargin = mEmptyTopDescTop
        }
    }

    private fun setEmptyBottomDesc(mBottomDesc: CharSequence) {
        this.mBottomDesc = mBottomDesc
        if (!mBottomDesc.isNullOrEmpty()) {
            binding.mEmptyBottomDesc.text = mBottomDesc
            binding.mEmptyBottomDesc.visibility = VISIBLE
        } else {
            binding.mEmptyBottomDesc.visibility = GONE
        }
    }


    private fun setEmptyBottomDescTop(mBottomDescTop: Int) {
        this.mBottomDescTop = mBottomDescTop
        if (mBottomDescTop >= 0) {
            val layoutParams =
                binding.mEmptyBottomDesc.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.topMargin = mBottomDescTop
        }
    }


    private fun setEmptyClickText(mClickText: CharSequence) {
        this.mClickText = mClickText
        if (mClickText.isNotEmpty()) {
            binding.mClickText.text = mClickText
        }
    }

    /**
     * 设置背景的时候才能展示按钮
     * @param backgroundRes Int
     */
    private fun setEmptyClickBackground(backgroundRes: Int) {
        this.backgroundRes = backgroundRes
        if (backgroundRes > 0) {
            binding.mClickText.setBackgroundResource(backgroundRes)
            binding.mClickText.visibility = VISIBLE
        } else {
            binding.mClickText.visibility = GONE
        }
    }

    private fun setEmptyClickTextColor(clickTextColor: Int) {
        this.clickTextColor = clickTextColor
        if (clickTextColor > 0) {
            binding.mClickText.setTextColor(ContextCompat.getColor(mContext, clickTextColor))
        }
    }

    private fun setEmptyClickTextSize(mClickTextSize: Float) {
        this.mClickTextSize = mClickTextSize
        if (mClickTextSize > 0) {
            binding.mClickText.setTextSize(mClickTextSize)
        }
    }

    private fun setEmptyClickTextTop(mClickTextTop: Int) {
        this.mClickTextTop = mClickTextTop
        if (mClickTextTop >= 0) {
            val layoutParams = binding.mClickText.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.topMargin = mClickTextTop
        }
    }
    /******************************** 风格设置**********************************************/
    /**
     *  全量设置方法(排除了 设置文字间距)
     * @param title String
     * @param titleColor Int
     * @param titleTextSize Float
     * @param topDesc String
     * @param topDescColor Int
     * @param topDescTextSize Float
     * @param clickText String
     * @param clickTextColor Int
     * @param clickTextSize Float
     * @param clickBackground Int
     */
    fun setStyle(
        title: CharSequence,
        titleColor: Int,
        titleTextSize: Float = 12f,
        topDesc: CharSequence,
        topDescColor: Int,
        topDescTextSize: Float = 12f,
        clickText: CharSequence,
        clickTextColor: Int,
        clickTextSize: Float = 12f,
        clickBackground: Int,
        bottomDesc: CharSequence,
        bottomDescColor: Int,
        bottomDescSize: Float = 12f,
    ) {

        setTitle(title)
        setTitleColor(titleColor)
        setTitleTextSize(titleTextSize)

        setEmptyTopDesc(topDesc)
        setEmptyTopDescTextSize(topDescTextSize)
        setEmptyTopDescColor(topDescColor)

        setEmptyClickText(clickText)
        setEmptyClickTextColor(clickTextColor)
        setEmptyClickTextSize(clickTextSize)
        setEmptyClickBackground(clickBackground)

        setEmptyBottomDesc(bottomDesc)
        setEmptyBottomDescColor(bottomDescColor)
        setEmptyBottomTextSize(bottomDescSize)


        if (title.isEmpty()) {
            binding.mEmptyTitle.visibility = View.GONE
        } else {
            binding.mEmptyTitle.visibility = View.VISIBLE
        }

        if (topDesc.isEmpty()) {
            binding.mEmptyTopDesc.visibility = View.GONE
        } else {
            binding.mEmptyTopDesc.visibility = View.VISIBLE
        }

        if (clickBackground > 0) {
            binding.mClickText.visibility = View.VISIBLE
        } else {
            binding.mClickText.visibility = View.GONE
        }

        if (bottomDesc.isEmpty()) {
            binding.mEmptyBottomDesc.visibility = View.GONE
        } else {
            binding.mEmptyBottomDesc.visibility = View.VISIBLE
        }
    }

    /**
     * 设置文字距离上边距位置
     * @param titleMarginTop Int
     * @param topDescMarginTop Int
     * @param clickMarginTop Int
     * @param bottomDescMarginTop Int
     */
    fun setEmptyTextMarginTop(
        titleMarginTop: Int, topDescMarginTop: Int, clickMarginTop: Int, bottomDescMarginTop: Int
    ) {
        setTitleTop(titleMarginTop)
        setEmptyBottomDescTop(bottomDescMarginTop)
        setEmptyClickTextTop(clickMarginTop)
        setEmptyTopDescTop(topDescMarginTop)
    }

    /**
     * 顶部展示文字 下边展示图片
     * @param title CharSequence
     * @param titleTextColor Int
     * @param titleTextSize Float
     * @param clickBackground Int
     * @param  marginTop Int
     * @param listenerArgs Array<out OnEmptyClickListener>  可传参数  图片点击事件
     *
     */
    fun setStyleTitleAndImage(
        title: CharSequence,
        titleTextColor: Int,
        titleTextSize: Float,
        clickBackground: Int,
        marginTop: Int,
        vararg listenerArgs: OnEmptyClickListener
    ) {
        setEmptyClickTextTop(marginTop)
        if (listenerArgs.size > 0) {
            this.listener = listenerArgs[0] as OnEmptyClickListener
        }
        setStyle(
            title,
            titleTextColor,
            titleTextSize,
            "",
            -1,
            0f,
            "",
            -1,
            0f,
            clickBackground,
            "",
            -1,
            0f
        )
    }

    /**
     * 顶部展示图片 下边展示文字
     * @param title CharSequence
     * @param titleTextColor Int
     * @param titleTextSize Float
     * @param clickBackground Int
     * @param listenerArgs Array<out OnEmptyClickListener>  可传参数  图片点击事件
     * @param marginTop Int 文字距离图的距离
     */
    fun setStyleImageAndTitle(
        title: CharSequence,
        titleTextColor: Int,
        titleTextSize: Float,
        clickBackground: Int,
        marginTop: Int,
        vararg listenerArgs: OnEmptyClickListener
    ) {
        if (listenerArgs.size > 0) {
            this.listener = listenerArgs[0] as OnEmptyClickListener
        }
        setEmptyBottomDescTop(marginTop)
        setStyle(
            "",
            -1,
            0f,
            "",
            -1,
            0f,
            "",
            -1,
            0f,
            clickBackground,
            title,
            titleTextColor,
            titleTextSize
        )
    }

    /**
     *  按钮+文字
     * @param title CharSequence
     * @param titleTextColor Int
     * @param titleTextSize Float
     * @param clickTitle CharSequence
     * @param clickTextColor Int
     * @param clickTextSize Float
     * @param clickBackground Int
     * @param listenerArgs Array<out OnEmptyClickListener>
     * @param marginTop Int 文字距离图的距离
     */

    fun setStyleButtonAndTitle(
        title: CharSequence,
        titleTextColor: Int,
        titleTextSize: Float,
        clickTitle: CharSequence,
        clickTextColor: Int,
        clickTextSize: Float,
        clickBackground: Int,
        marginTop: Int,
        vararg listenerArgs: OnEmptyClickListener
    ) {
        if (listenerArgs.size > 0) {
            this.listener = listenerArgs[0] as OnEmptyClickListener
        }
        setEmptyTopDescTop(marginTop)
        setStyle(
            "",
            -1,
            0f,
            "",
            -1,
            0f,
            clickTitle,
            clickTextColor,
            clickTextSize,
            clickBackground,
            title,
            titleTextColor,
            titleTextSize
        )
    }

    /**
     *  文字加按钮
     * @param title CharSequence
     * @param titleTextColor Int
     * @param titleTextSize Float
     * @param clickTitle CharSequence
     * @param clickTextColor Int
     * @param clickTextSize Float
     * @param clickBackground Int
     * @param listenerArgs Array<out OnEmptyClickListener>
     * @param marginTop Int 文字距离图的距离
     */

    fun setStyleTitleAndButton(
        title: CharSequence,
        titleTextColor: Int,
        titleTextSize: Float,
        clickTitle: CharSequence,
        clickTextColor: Int,
        clickTextSize: Float,
        clickBackground: Int,
        marginTop: Int,
        vararg listenerArgs: OnEmptyClickListener
    ) {
        if (listenerArgs.size > 0) {
            this.listener = listenerArgs[0] as OnEmptyClickListener
        }
        setEmptyClickTextTop(marginTop)
        setStyle(
            title,
            titleTextColor,
            titleTextSize,
            "",
            -1,
            0f,
            clickTitle,
            clickTextColor,
            clickTextSize,
            clickBackground,
            "",
            -1,
            0f
        )
    }


}