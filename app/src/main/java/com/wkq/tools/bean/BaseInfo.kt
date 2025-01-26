package com.wkq.tools.bean

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/6 14:24
 *
 */
class BaseInfo<T> (    val code: Int,
                       val data: T,
                       val message: String,
                       val signature: String,
                       val timestamp: Int){
}