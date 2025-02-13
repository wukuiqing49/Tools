package com.wkq.tools.util

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/2/6 16:40
 *
 *@Desc:
 */
interface MediaPlayerListener {
    fun onErr(messageCode: Int)
    fun finish()
    fun prepare()
}