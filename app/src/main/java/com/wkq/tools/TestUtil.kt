package com.wkq.tools

import com.wkq.net.DataCallback
import com.wkq.net.NetConstant
import com.wkq.net.NetWork
import com.wkq.tools.net.Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/1/23 9:20
 *
 *@Desc:
 */


suspend fun <T>test(hashMap:HashMap<String, String>,callback: DataCallback<T>){

    hashMap.put("username", "wkq")
    hashMap.put("password", "123456")
    flow{
        var result = NetWork.getInstance().create(Api::class.java, NetConstant.BASE_URL) .list_type(hashMap)
        emit(result)
    }.collect{result ->{

    }}
}

suspend fun <T>test2( getData:(hashMap:HashMap<String, String>)->T,){

    flow{
        var result = getData
        emit(result)
    }.collect{result ->{

    }}
}



//fun fetchUsers(): Flow<List<User>> = flow {
//    val users = apiService.getUsers()
//    emit(users)
//}.catch { e ->
//    // 处理网络请求的错误
//}