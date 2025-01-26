package com.wkq.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap
import kotlin.jvm.Throws

/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途:  添加Header数据拦截器
 */

class HttpHeadersInterceptor : Interceptor {
    var header:HashMap<String,String>?=null
    constructor(header:HashMap<String,String>?){
        this.header=header
    }


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val  headerBulider = request.headers.newBuilder()
        if (header!=null){
            header!!.forEach {
                headerBulider.add(it.key,it.value)
            }
        }

        val newRequest = request.newBuilder()
            .headers(headerBulider.build())
            .build()

        return chain.proceed(newRequest)

    }

    /**
     * 1.生成的字符串每个位置都有可能是str中的一个字母或数字，需要导入的包是import java.util.Random;
     * @param length
     * @return
     */
    fun createRandomStr(length: Int): String? {
        val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val stringBuffer = StringBuffer()
        for (i in 0 until length) {
            val number = random.nextInt(62)
            stringBuffer.append(str[number])
        }
        return stringBuffer.toString()
    }
}
