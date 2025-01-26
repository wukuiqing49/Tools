package com.wkq.net

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wkq.net.interceptor.HttpHeadersInterceptor
import com.wkq.net.retrofit.RetrofitFactory
import com.wkq.net.util.MMKVUtils
import retrofit2.Retrofit


/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途:  网络请求
 */


class NetWork {
    private var retrofit: Retrofit? = null


    private fun getRetrofitInstance(baseUrl: String? = ""): Retrofit {
        if (retrofit == null) {
            var urlHost: String? = ""
            if (TextUtils.isEmpty(baseUrl)) {
                urlHost = MMKVUtils.getString("baseUrl")
            } else {
                urlHost = baseUrl
            }
            val headerHashMapStr = MMKVUtils.getString("headerHashMap");
            var headerHashMap=HashMap<String,String>();
            if (headerHashMap != null) {
                val empMapType = object : TypeToken<HashMap<String, String>>() {}.type
                 headerHashMap = Gson().fromJson<HashMap<String, String>>(headerHashMapStr, empMapType)
            }
            retrofit =
                RetrofitFactory.getInstance()
                    .setReadTimeout(MMKVUtils.getLong("readTimeOut"))
                    .setConnectTimeout(MMKVUtils.getLong("connectTimeOut"))
                    .setHeaderInterceptor(HttpHeadersInterceptor(headerHashMap))
                    .bulid(urlHost!!)
        }
        return retrofit!!
    }
    fun <T> create(service: Class<T>, baseUrl: String? = ""): T {
        return getRetrofitInstance(baseUrl).create(service)
    }



    companion object {
        fun getInstance(): NetWork {
            val instance: NetWork by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { NetWork() }
            return instance
        }
    }
}