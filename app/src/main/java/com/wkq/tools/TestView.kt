package com.wkq.tools

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wkq.net.NetWork
import com.wkq.net.model.ResultInfo
import com.wkq.tools.bean.UserInfo
import com.wkq.tools.net.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/14 17:34
 *
 */
class TestView : ViewModel() {
    val resultMutableLiveData =MutableLiveData<ResultInfo<UserInfo>>()
    val flow = MutableStateFlow(1)
    fun testRequest(hashMap: HashMap<String, String>?) {

        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")
        viewModelScope.launch {
            resultMutableLiveData.value=NetWork.getInstance().create(Api::class.java).login(hashMap)
        }
    }

    fun test(){

    }
}