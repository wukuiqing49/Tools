package com.wkq.net

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import com.wkq.net.model.BaseInfo
import com.wkq.net.model.FlowThreeZipBean
import com.wkq.net.model.FlowTwoZipBean
import com.wkq.net.model.ResultInfo
import com.wkq.net.util.FlowZipUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/1/23 9:51
 *
 *@Desc:
 */
class FlowHelper : LifecycleOwner {
    private val mRegistry = LifecycleRegistry(this)

    private constructor() {
        lifecycle.addObserver(object : DefaultLifecycleObserver {

            override fun onCreate(owner: LifecycleOwner) {
                Log.e("网络请求生命周期:", "onCreate")
            }

            override fun onResume(owner: LifecycleOwner) {
                Log.e("网络请求生命周期:", "onResume")
            }

            override fun onPause(owner: LifecycleOwner) {
                Log.e("网络请求生命周期:", "onPause")
            }

            override fun onStart(owner: LifecycleOwner) {
                Log.e("网络请求生命周期:", "onStart")
            }

            override fun onStop(owner: LifecycleOwner) {

                Log.e("网络请求生命周期:", "onStop")
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                if (job != null) {
                    job!!.cancel()
                }
                Log.e("网络请求生命周期:", "onDestroy")
            }
        })
    }

    companion object {
        fun getInstance(): FlowHelper {
            val instance: FlowHelper by lazy(
                mode = LazyThreadSafetyMode.SYNCHRONIZED
            ) { FlowHelper() }
            return instance
        }
    }

    var job: Job? = null

    /**
     * 普通请求
     * @param method SuspendFunction0<ResultInfo<T>>
     * @param call DataCallback<T>
     * @return Job?
     */
    fun <T> request(method: suspend () -> ResultInfo<T>, call: DataCallback<T>): Job? {
        job = lifecycleScope.launch {
            mRegistry.currentState = Lifecycle.State.CREATED
            mRegistry.currentState = Lifecycle.State.STARTED
            val result = method()

            if (result.isSuccess) {
                call.onSuccess(result.data)
            } else {
                call.onFail(result.code, result.msg)
            }
            mRegistry.currentState = Lifecycle.State.DESTROYED

        }

        return job

    }


    // vararg 可变参数
    fun <T> requestTwoFlowParallel(
        firstRequest: suspend () -> T, secondRequest: suspend () -> T, call: DataCallback<T>
    ): Job? {
        job = lifecycleScope.launch {
            mRegistry.currentState = Lifecycle.State.CREATED

            val firstResultFlow = FlowZipUtils.getFlow {
                firstRequest()
            }

            val secondResultFlow = FlowZipUtils.getFlow {
                firstRequest()
            }

            flow<T> {

            }.collect { result ->

            }
        }
        return job

    }


    /**
     * 并行网络请求 单独返回数据  可变参数方式 (单独返回)
     * @param methods Array<out SuspendFunction0<ResultInfo<T>>>
     * @param call DataCallback<T>
     * @return Job?
     */
    fun <T> requestSingleParallel(call: DataCallback<T>, vararg methods: suspend () -> T): Job? {

        methods.forEach {
            job = lifecycleScope.launch {

                mRegistry.currentState = Lifecycle.State.CREATED
                FlowZipUtils.getFlow { it() }.collect {
                    if (it is ResultInfo<*>) {
                        val r = it as ResultInfo<*>
                        if (it.isSuccess) {
                            call.onSuccess(it)
                        } else {
                            call.onFail(it.code, it.msg)
                        }
                    } else {
                        call.onFail(
                            NetConstant.ERROR_DATA_FORMAT.toString(),
                            NetConstant.MESSAGE_DATA_FORMAT_ERROR
                        )
                    }
                }
            }
        }
        return job

    }

    /**
     *  并行网络请求 单独返回数据  请求集合形式 (单独返回)
     * @param methodList MutableList<SuspendFunction0<Any>>
     * @param call DataCallback<ResultInfo<*>>
     * @return Job?
     */
    fun requestSingleParallel(
        methodList: MutableList<suspend () -> Any>, call: DataCallback<ResultInfo<*>>
    ): Job? {

        methodList.forEach {
            job = lifecycleScope.launch {
                mRegistry.currentState = Lifecycle.State.CREATED
                FlowZipUtils.getFlow { it() }.collect {
                    mRegistry.currentState = Lifecycle.State.STARTED
                    if (it is ResultInfo<*>) {
                        val r = it as ResultInfo<*>
                        if (it.isSuccess) {
                            call.onSuccess(it)
                        } else {
                            call.onFail(it.code, it.msg)
                        }
//                        mRegistry.currentState = Lifecycle.State.DESTROYED
                    } else {
                        call.onFail(
                            NetConstant.ERROR_DATA_FORMAT.toString(),
                            NetConstant.MESSAGE_DATA_FORMAT_ERROR
                        )
                    }
                }
            }


        }
        return job

    }




    /**
     * 并行 合并两个网络请求 (统一返回)
     * @param first SuspendFunction0<F>
     * @param second SuspendFunction0<S>
     * @param call DataCallback<FlowTwoZipBean<F, S>>
     * @return Job?
     */
    fun <F, S> requestMergeFlowParallel(
        first: suspend () -> F, second: suspend () -> S, call: DataCallback<FlowTwoZipBean<F, S>>
    ): Job? {

        job = lifecycleScope.launch {
            val firstFlow = FlowZipUtils.getFlow { first() }
            val secondFlow = FlowZipUtils.getFlow { second() }
            FlowZipUtils.zipTwoFlows<F, S, FlowTwoZipBean<F, S>>(firstFlow, secondFlow, { firstResult, secondResult -> FlowTwoZipBean<F, S>(firstResult, secondResult) })
                .collect {
                    call.onSuccess(it)
                }
        }

        return job

    }

    /**
     * 并行 合并三个网络请求 统一返回(统一返回)
     * @param first SuspendFunction0<F>
     * @param second SuspendFunction0<S>
     * @param three SuspendFunction0<T>
     * @param call DataCallback<FlowThreeZipBean<F, S, T>>
     * @return Job?
     */
    fun <F, S,T> requestMergeFlowParallel(
        first: suspend () -> F, second: suspend () -> S,three: suspend () -> T, call: DataCallback<FlowThreeZipBean<F,S,T>>
    ): Job? {
        mRegistry.currentState = Lifecycle.State.CREATED
        job = lifecycleScope.launch {
            mRegistry.currentState = Lifecycle.State.STARTED
            val firstFlow = FlowZipUtils.getFlow { first() }
            val secondFlow = FlowZipUtils.getFlow { second() }
            val threeFlow = FlowZipUtils.getFlow { three() }
            FlowZipUtils.zipThreeFlows<F, S,T, FlowThreeZipBean<F, S,T>>(firstFlow, secondFlow,threeFlow, { firstResult, secondResult ,threeResult-> FlowThreeZipBean<F, S,T>(firstResult, secondResult,threeResult) })
                .collect {
                    call.onSuccess(it)
                    mRegistry.currentState = Lifecycle.State.DESTROYED
                }
        }
        return job
    }


    /**
     * flow(冷流) 流串行执行
     * @param call DataCallback<T>
     * @param methods Array<out SuspendFunction0<T>>
     * @return Job?
     */

    fun <T> requestFlowSerial(
        call: DataCallback<T>, vararg methods: suspend () -> T
    ): Job? {
        job = lifecycleScope.launch {
            mRegistry.currentState = Lifecycle.State.CREATED
            flow<T> {
                methods.forEach {
                    mRegistry.currentState = Lifecycle.State.STARTED
                    emit(it())
                }
            }.collect { result ->
                if (result is ResultInfo<*>) {
                    if (result.isSuccess) {
                        call.onSuccess(result)
                    } else {
                        call.onFail(result.code, result.msg)
                    }
                } else {
                    call.onFail(
                        NetConstant.ERROR_DATA_FORMAT.toString(),
                        NetConstant.MESSAGE_DATA_FORMAT_ERROR
                    )
                }
//                mRegistry.currentState = Lifecycle.State.DESTROYED
            }
        }
        return job

    }


    fun  <T>requestFlowSerial(methodList: MutableList<suspend () -> T>, call: DataCallback<T>
    ): Job? {
        job = lifecycleScope.launch {
            mRegistry.currentState = Lifecycle.State.CREATED
            flow<T> {
                methodList.forEach {
                    mRegistry.currentState = Lifecycle.State.STARTED
                    emit(it())
                }
            }.collect { result ->
                if (result is ResultInfo<*>) {
                    if (result.isSuccess) {
                        call.onSuccess(result)
                    } else {
                        call.onFail(result.code, result.msg)
                    }
                } else {
                    call.onFail(
                        NetConstant.ERROR_DATA_FORMAT.toString(),
                        NetConstant.MESSAGE_DATA_FORMAT_ERROR
                    )
                }
            }
        }
        return job

    }


    fun  <T>requestMergeFlowSerial(methodList: MutableList<suspend () -> T>, call: DataCallback<T>
    ): Job? {

        methodList.map {
            FlowZipUtils.getFlow(it)
        }

        job = lifecycleScope.launch {
            mRegistry.currentState = Lifecycle.State.CREATED
            flow<T> {
                methodList.forEach {
                    mRegistry.currentState = Lifecycle.State.STARTED
                    emit(it())
                }
            }.collect { result ->
                if (result is ResultInfo<*>) {
                    if (result.isSuccess) {
                        call.onSuccess(result)
                    } else {
                        call.onFail(result.code, result.msg)
                    }
                } else {
                    call.onFail(
                        NetConstant.ERROR_DATA_FORMAT.toString(),
                        NetConstant.MESSAGE_DATA_FORMAT_ERROR
                    )
                }
            }
        }
        return job

    }

    fun cancel() {
        if (job != null) job!!.cancel()
        mRegistry.currentState = Lifecycle.State.DESTROYED
    }

    override val lifecycle: Lifecycle get() = mRegistry


}