package com.wkq.net.util

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/1/23 16:26
 *
 *@Desc:
 */
class ContextProvider : ContentProvider() {

    companion object {
        @Volatile
        private var instance: ContextProvider? = null

        // 获取 ContentProvider 单例
        fun getInstance(): ContextProvider =
            instance ?: synchronized(this) {
                instance ?: ContextProvider().also { instance = it }
            }
    }

    private var appContext: Context? = null

    override fun onCreate(): Boolean {
        appContext = context?.applicationContext // 获取 Application Context，避免泄漏
        instance = this // 设置单例实例
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        // 这里写查询逻辑
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        // 这里写插入逻辑
        return null
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        // 这里写更新逻辑
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        // 这里写删除逻辑
        return 0
    }

    override fun getType(uri: Uri): String? {
        // 返回 MIME 类型
        return null
    }

    fun getApplicationContext(): Context? {
        return appContext
    }
}
