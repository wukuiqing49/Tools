package com.wkq.net.retrofit


import android.util.Log
import com.wkq.net.gson.CustomGsonConverterFactory
import com.wkq.net.interceptor.HttpHeadersInterceptor
import com.wkq.net.util.MMKVUtils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途: 初始化 Retrofit数据
 */


class RetrofitFactory {

    var okHttpClientBulider: OkHttpClient.Builder? = null

    private constructor() {
        okHttpClientBulider = OkHttpClient().newBuilder()

    }

    companion object {
        fun getInstance(): RetrofitFactory {
            val instance: RetrofitFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitFactory() }
            return instance
        }
    }

    fun setReadTimeout(read_time_out: Long? = 5000L): RetrofitFactory {
        if (okHttpClientBulider != null) read_time_out?.let {
            okHttpClientBulider?.readTimeout(
                it, TimeUnit.MILLISECONDS
            )
        }
        return this
    }

    fun setConnectTimeout(connect_time_out: Long? = 5000L): RetrofitFactory {
        if (okHttpClientBulider != null) connect_time_out?.let {
            okHttpClientBulider?.connectTimeout(
                it, TimeUnit.MILLISECONDS
            )
        }
        return this
    }

    fun setHeaderInterceptor(headerInterceptor: Interceptor? = null): RetrofitFactory {
        if (headerInterceptor != null) {
            okHttpClientBulider?.addInterceptor(headerInterceptor)
        } else {
            okHttpClientBulider?.addInterceptor(HttpHeadersInterceptor(null))
        }
        return this
    }

    fun setResponseInterceptor(headerInterceptor: Interceptor? = null): RetrofitFactory {
        if (headerInterceptor != null) {
            okHttpClientBulider?.addInterceptor(headerInterceptor)
        } else {
            okHttpClientBulider?.addInterceptor(HttpHeadersInterceptor(null))
        }
        return this
    }

    fun bulid(baseUrl: String): Retrofit {
        addLog(MMKVUtils.getBoolean("isOpen"))
        return Retrofit.Builder().client(okHttpClientBulider?.build()).baseUrl(baseUrl)//设置BASEURL(以/结尾)
            .addConverterFactory(CustomGsonConverterFactory.create()).addCallAdapterFactory(ApiResultCallAdapterFactory())// 数据转换
            .build()
    }
    fun bulidCallback(baseUrl: String): Retrofit {
        addLog(MMKVUtils.getBoolean("isOpen"))
        return Retrofit.Builder()
            .client(okHttpClientBulider?.build())
            .baseUrl(baseUrl)//设置BASEURL(以/结尾)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 请求日志
     * @param isShowLog Boolean
     */
    private fun addLog(isShowLog: Boolean) {
        if (isShowLog) {
            // 日志拦截器
            val log = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.i("网络请求日志:", it);
            })
            log.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBulider!!.addInterceptor(log)

        }
    }

}