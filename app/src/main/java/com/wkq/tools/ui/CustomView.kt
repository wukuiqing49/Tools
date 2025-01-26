package com.wkq.tools.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import com.wkq.tools.TestView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/15 10:23
 *
 */
class CustomView : LinearLayout, LifecycleOwner {
    private val liveData = MutableLiveData<Int>()

    private val mRegistry = LifecycleRegistry(this)
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    )




    private val netViewModel by lazy {
        TestView()

    }

    init {

        val stateFlow = MutableStateFlow(0)


        netViewModel.resultMutableLiveData.observe(this){

        }
        netViewModel.testRequest(null)

        CoroutineScope(Dispatchers.IO).launch {
            stateFlow.collect{v->{

            }}
        }

    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mRegistry.currentState = Lifecycle.State.CREATED

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mRegistry.currentState = Lifecycle.State.DESTROYED

    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (visibility == VISIBLE) {
            mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
            mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        }else if (visibility == GONE || visibility == INVISIBLE){
            mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)

        }
    }

    override val lifecycle: Lifecycle
        get() = mRegistry

}