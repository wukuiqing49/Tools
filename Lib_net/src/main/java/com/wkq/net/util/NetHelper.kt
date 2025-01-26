package com.wkq.net.util

import com.google.gson.JsonObject
import com.wkq.net.NetConstant.SUCESSCODE
import com.wkq.net.model.ResultInfo
import com.wkq.net.model.SpecialCodeBean
import com.wkq.net.observable.SpecialCodeObservable.Companion.newInstance
import com.wkq.net.util.MMKVUtils.getStringSet


/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/14 16:17
 *
 */
object NetHelper {
    /**
     * 获取code
     * @param jsonObject JsonObject
     * @return String
     */
    fun getNetCode(jsonObject: JsonObject):String{
        var errorCode="-1"
        val codeKey = MMKVUtils.getStringSet("CodeKey") as HashSet<String>?
        val successCodeKey = getStringSet("SuccessCodeKey") as java.util.HashSet<String>?
        // 处理 code
        if (codeKey != null) {
            for (cKey in codeKey) {
                if (jsonObject.has(cKey)) {
                    if (successCodeKey!!.contains(jsonObject.get(cKey).getAsString())) {
                        errorCode = SUCESSCODE.toString() + ""
                    } else {
                        errorCode = jsonObject.get(cKey).getAsString()
                    }
                    break
                }
            }
        }
        return errorCode
    }

    fun getNetMsg(jsonObject: JsonObject):String{
        var errorMsg=""
        val msgKey = getStringSet("MsgKey") as java.util.HashSet<String>?

        // 处理 code
        // 处理msg
        for (mKey in msgKey!!) {
            if (jsonObject.has(mKey)) {
                errorMsg = jsonObject.get(mKey).getAsString()
                break
            }
        }
        return errorMsg
    }

    /**
     * 处理特殊code
     * @param info ResultInfo<Any>
     */
    fun processSpecialCode(info: ResultInfo<Any>){
        val errorMsg=""
        val specialCodeKey = getStringSet("SpecialCodeKey") as java.util.HashSet<String>?
        //特殊code处理
        if (specialCodeKey!!.contains(info.code)) {
            newInstance().updateSpecialCode(SpecialCodeBean(info.code, errorMsg, info.data))
        }
    }

    /**
     * 处理成功的状态
     * @param info ResultInfo<Any>
     */
    fun processSuccessStatus(jsonObject: JsonObject):Boolean{
        var isSuccess=false
        val codeKey = MMKVUtils.getStringSet("CodeKey") as HashSet<String>?
        val successCodeKey = getStringSet("SuccessCodeKey") as java.util.HashSet<String>?
        // 处理 code
        if (codeKey != null) {
            for (cKey in codeKey) {
                if (jsonObject.has(cKey)) {
                    if (successCodeKey!!.contains(jsonObject.get(cKey).getAsString())) {
                        isSuccess = true
                    } else {
                        isSuccess = false
                    }
                    break
                }
            }
        }
        return isSuccess
    }
}