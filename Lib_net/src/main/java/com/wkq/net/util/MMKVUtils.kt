package com.wkq.net.util

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import com.wkq.net.NetConstant

/**
 * @author :Create by Xia燚
 * 时间：2022/7/4
 * 邮箱：XiahaotianV@163.com
 * MMKV 存储
 **/
object MMKVUtils {

    //运维人员名称
    const val OPERATIONS_NAME = "operations_name"


    var mmkv: MMKV? = null

    init {
        mmkv = MMKV.defaultMMKV()
    }


    fun put(key: String, value: Any?): Boolean {
        return when (value) {
            is String -> mmkv?.encode(key, value)!!
            is Float -> mmkv?.encode(key, value)!!
            is Boolean -> mmkv?.encode(key, value)!!
            is Int -> mmkv?.encode(key, value)!!
            is Long -> mmkv?.encode(key, value)!!
            is Double -> mmkv?.encode(key, value)!!
            is ByteArray -> mmkv?.encode(key, value)!!
            else -> false
        }
    }


    /**
     * 这里使用安卓自带的Parcelable序列化，它比java支持的Serializer序列化性能好些
     */

    fun <T : Parcelable> put(key: String, t: T?): Boolean {
        if (t == null) {
            return false
        }
        return mmkv?.encode(key, t)!!
    }

    fun put(key: String, sets: HashSet<String>): Boolean {
        if (sets == null) {
            return false
        }
        return mmkv?.encode(key, sets)!!
    }

    fun getInt(key: String): Int? {
        return mmkv?.decodeInt(key, 0)
    }

    fun getDouble(key: String): Double? {
        return mmkv?.decodeDouble(key, 0.00)
    }

    fun getLong(key: String): Long {
        return mmkv?.decodeLong(key, 5000L) ?: 5000L
    }

    fun getBoolean(key: String): Boolean {
        return mmkv?.decodeBool(key, false) ?: false
    }

    fun getFloat(key: String): Float? {
        return mmkv?.decodeFloat(key, 0F)
    }

    fun getByteArray(key: String): ByteArray? {
        return mmkv?.decodeBytes(key)
    }

    fun getString(key: String): String {
        return mmkv?.decodeString(key, "") ?: ""
    }

    /**
     * SpUtils.getParcelable<Class>("")
     */
    inline fun <reified T : Parcelable> getParcelable(key: String): T? {
        return mmkv?.decodeParcelable(key, T::class.java)
    }

    fun getStringSet(key: String): MutableSet<String>? {
        val hashSet = HashSet<String>()
        hashSet.add(NetConstant.SUCESSCODE.toString())
        hashSet.add("0")
        return mmkv?.decodeStringSet(key, HashSet<String>())
    }

    fun removeKey(key: String) {
        mmkv?.removeValueForKey(key)
    }

    fun clearAll() {
        mmkv?.clearAll()
    }

}
