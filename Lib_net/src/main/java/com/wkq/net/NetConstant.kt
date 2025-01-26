package com.wkq.net

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/3 10:21
 *
 */
object NetConstant {
    val BASE_URL = "https://api.lingdalingda.com/"
    val CONNECTTIME = 5000L

    //成功Code
    val SUCESSCODE = 200
    val MESSAGE_SUCCESS: String = "成功"

    //错误Code
    val FAILCODE = -2
    val MESSAGE_ERROR: String = "服务器异常"

    //网络异常Code
    val ERROR= -1
    val ERROR_MSG: String = "网络异常"

    //数据格式异常
    val ERROR_DATA_FORMAT = -3
    val MESSAGE_DATA_FORMAT_ERROR: String = "数据格式异常"





}