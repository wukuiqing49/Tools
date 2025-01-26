package com.wkq.tools.net



import com.wkq.net.model.ResultInfo
import com.wkq.net.retrofit.ApiResult
import com.wkq.tools.bean.Banner
import com.wkq.tools.bean.Banner2
import com.wkq.tools.bean.BaseInfo
import com.wkq.tools.bean.ClassifyInfo
import com.wkq.tools.bean.UserInfo
import retrofit2.Call
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/3 16:12
 *
 */
interface Api {

    //首页列表
    @GET("api/takeOut/list_type?")
   suspend fun list_type(@QueryMap maps: Map<String, String>): ResultInfo<List<ClassifyInfo>>

    @GET("banner/json")
    suspend fun banner(@QueryMap map: HashMap<String, String>): ResultInfo<List<Banner>>



    @POST("user/login")
    suspend fun login(@QueryMap map: HashMap<String, String>): ResultInfo<UserInfo>



    @GET("banner/json")
    suspend  fun banner2(@QueryMap map: HashMap<String, String>): ResultInfo<List<Banner>>
    @POST("user/login")
    suspend fun login2(@QueryMap map: HashMap<String, String>): ResultInfo<UserInfo>

}