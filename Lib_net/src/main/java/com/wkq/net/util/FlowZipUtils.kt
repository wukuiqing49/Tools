package com.wkq.net.util

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/1/24 17:33
 *
 *@Desc: flow 合并方法
 */
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip


object FlowZipUtils {

    /**
     *
     * @param flow1 Flow<R1> R1 表示第一个Flow 结果的泛型
     * @param flow2 Flow<R2>表示第二个Flow 结果的泛型
     * @param transform Function2<R1, R2, M>
     * @return Flow<M>表示合并请求后输出的合并数据
     */
    fun <R1, R2, M> zipTwoFlows(flow1: Flow<R1>, flow2: Flow<R2>, transform: (R1, R2) -> M): Flow<M> {
        return flow1.zip(flow2, transform)
    }

    /**
     * 获取一个数据流(冷流)
     * @param method SuspendFunction0<T>
     * @return Flow<T>
     */
    fun <T> getFlow(method: suspend () -> T): Flow<T> {
        return flow<T> {
            emit(method())
        }
    }


    /**
     *
     * @param flow1 Flow<R1>  表示第一个Flow 结果的泛型
     * @param flow2 Flow<R2> 表示第二个Flow 结果的泛型
     * @param flow3 Flow<R3>  表示第三个Flow 结果的泛型
     * @param transform Function3<R1, R2, R3, M>
     * @return Flow<M> 表示合并请求后输出的合并数据
     */
    fun <R1, R2, R3, M> zipThreeFlows(
        flow1: Flow<R1>,
        flow2: Flow<R2>,
        flow3: Flow<R3>,
        transform: (R1, R2, R3) -> M
    ): Flow<M> {
        return flow1.zip(flow2) { a, b -> Pair(a, b) }.zip(flow3) { (a, b), c -> transform(a, b, c) }
    }

}



