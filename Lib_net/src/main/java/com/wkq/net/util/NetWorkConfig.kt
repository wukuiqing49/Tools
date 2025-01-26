package com.wkq.net.util

import com.google.gson.Gson


/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途:  网络请求配置
 */


class NetWorkConfig private constructor(val baseUrl: String, val readTimeOut: Long? = 5000L,
                                        val connectTimeOut: Long? = 5000L, val headerHashMap: HashMap<String, String>? = null) {

    private  constructor( builder: Builder):this(builder.baseUrl,builder.readTimeOut,builder.connectTimeOut,builder.headerHashMap)

    class Builder() {
        var baseUrl: String = ""
        var readTimeOut: Long = 5000L
        var connectTimeOut: Long = 5000L
        var headerHashMap = HashMap<String, String>()

        var codeKey: String = ""
        var msgKey: String = ""
        var dataKey: String = ""
        private var isOpen: Boolean = false


        /**
         * 结果信息打印开关
         * @param isOpen Boolean
         * @return Builder
         */
        fun setIsLogcat(isOpen: Boolean): Builder {
            this.isOpen = isOpen
            MMKVUtils.put("isOpen",isOpen)
            return this
        }

        /**
         * 请求头数据
         * @param hashMap HashMap<String, String>
         * @return Builder
         */
        fun setHeaderHashMap(hashMap: HashMap<String, String>): Builder {
            this.headerHashMap = hashMap
            val HeaderJson = Gson().toJson(headerHashMap)
            MMKVUtils.put("headerHashMap",HeaderJson)
            return this
        }

        /**
         * baseUr
         * @param baseUrl String
         * @return Builder
         */
        fun setBaseUrl(baseUrl: String): Builder {
            this.baseUrl = baseUrl
            MMKVUtils.put("baseUrl",baseUrl)
            return this
        }

        /**
         * read 超时时间
         * @param time Long
         * @return Builder
         */
        fun setReadTimeOut(time: Long): Builder {
            this.readTimeOut = time
            MMKVUtils.put("readTimeOut",readTimeOut)
            return this
        }

        /**
         * 链接时常
         * @param time Long
         * @return Builder
         */
        fun setConnectTimeOut(time: Long): Builder {
            this.connectTimeOut = time
            MMKVUtils.put("connectTimeOut",connectTimeOut)
            return this
        }

        /**
         * 添加兼容的 结果数据 消息code 的Key (适配多服务器)
         * @param key String
         * @return Builder
         */
        fun addCodeKey(key: String): Builder {
           val hasSet= MMKVUtils.getStringSet("CodeKey") as HashSet
            if (!hasSet.contains(key))  hasSet.add(key)
            MMKVUtils.put("CodeKey",hasSet)
            return this
        }

        /**
         * 添加兼容的 结果数据 消息msg 的Key (适配多服务器)
         * @param key String
         * @return Builder
         */
        fun addMsgKey(key: String): Builder {


            val hasSet= MMKVUtils.getStringSet("MsgKey") as HashSet
            if (!hasSet.contains(key))  hasSet.add(key)
            MMKVUtils.put("MsgKey",hasSet)
            return this
        }

        /**
         * 添加兼容的 结果数据 消息结果的Key (适配多服务器)
         * @param key String
         * @return Builder
         */
        fun addDataKey(key: String): Builder {
            val hasSet= MMKVUtils.getStringSet("DataKey") as HashSet
            if (!hasSet.contains(key))  hasSet.add(key)
            MMKVUtils.put("DataKey",hasSet)
            return this
        }

        /**
         * 设置 成功的code
         * @param key String
         * @return Builder
         */
        fun addSuccessCodeKey(key: String): Builder {
            val hasSet= MMKVUtils.getStringSet("SuccessCodeKey") as HashSet
            if (!hasSet.contains(key)) {
                hasSet.add(key)
            }
            MMKVUtils.put("SuccessCodeKey",hasSet)
            return this
        }

        /**
         * 添加特殊账号chu'k'lu'i
         * @param key String
         * @return Builder
         */
        fun addSpecialCodeKey(key: String): Builder {
            val hasSet= MMKVUtils.getStringSet("SpecialCodeKey") as HashSet
            if (!hasSet.contains(key)) {
                hasSet.add(key)
            }
            MMKVUtils.put("SpecialCodeKey",hasSet)
            return this
        }

        /**
         * 创建
         * @return NetWorkConfig
         */
        fun build(): NetWorkConfig {
            return NetWorkConfig(baseUrl, readTimeOut, connectTimeOut, headerHashMap)
        }
    }


}

