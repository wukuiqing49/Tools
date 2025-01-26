package com.wkq.net

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/1/23 9:23
 *
 *@Desc:
 */
interface DataCallback<T> {
    fun onSuccess(t :T)
    fun onFail(code:String,msg:String)
}