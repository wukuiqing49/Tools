package com.wkq.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.internal.wait

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/14 17:29
 *
 */
open class BaseViewModel : ViewModel() {



    fun cancel() {
        if (viewModelScope!=null)
        viewModelScope.cancel()
    }

}