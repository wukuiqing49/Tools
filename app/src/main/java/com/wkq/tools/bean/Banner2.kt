package com.wkq.tools.bean

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/11 17:13
 *
 */


data class Banner2(
    val data: List<Data> = listOf(),
    val errorCode: Int = 0, // 0
    val errorMsg: String = ""
) {
    data class Data(
        val desc: String = "", // 一起来做个App吧
        val id: Int = 0, // 10
        val imagePath: String = "", // https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png
        val isVisible: Int = 0, // 1
        val order: Int = 0, // 1
        val title: String = "", // 一起来做个App吧
        val type: Int = 0, // 0
        val url: String = "" // https://www.wanandroid.com/blog/show/2
    )

}