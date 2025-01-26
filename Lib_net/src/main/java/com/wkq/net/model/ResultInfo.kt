package com.wkq.net.model

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/13 9:29
 *
 */
class ResultInfo<T>(var code: String, var msg: String, var data: T):BaseInfo(){
    var isSuccess=false
}