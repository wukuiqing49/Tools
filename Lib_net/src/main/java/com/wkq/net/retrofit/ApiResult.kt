package com.wkq.net.retrofit

import java.io.Serializable

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/11 16:21
 *
 */
open class ApiResult<T>(): Serializable {
    data class BizSuccess<T>(val errorCode: Int, val errorMsg: String, val data: T) : ApiResult<T>()
    data class BizError(val errorCode: Int, val errorMsg: String) : ApiResult<Nothing>()
    data class OtherError(val throwable: Throwable) : ApiResult<Nothing>()
}