package com.wkq.tools.decrypt

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/7/11 15:32
 *
 *@Desc:获取So库中的数据
 */
object DecryptUtil {
    init {
        System.loadLibrary("decrypt")
    }
    //获取加密后的数据
    private external fun getDecryptedData(): String

    fun getDecryptedStr(): String? {
        val seed = (System.currentTimeMillis() and 0xFFFFFFFF).toInt()
        return getDecryptedData()
    }
}