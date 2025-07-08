package com.wkq.tools

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.tencent.mmkv.MMKV
import com.wkq.net.NetWork
import com.wkq.net.model.SpecialCodeBean
import com.wkq.net.observable.SpecialCodeObservable
import com.wkq.net.util.NetWorkConfig
import com.wkq.ui.util.GreySkinUtil
import java.util.HashMap
import java.util.Observable
import java.util.Observer

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/3 16:12
 *
 */
    class CustomApplication : Application() ,Observer, Application.ActivityLifecycleCallbacks {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        SpecialCodeObservable.newInstance().addObserver(this)
        MMKV.initialize(this);
        val header = HashMap<String, String>()
        header.put("Phone-Model", "Android")
        header.put("Version", this.packageManager.getPackageInfo(packageName, 0).versionName)

        NetWorkConfig.Builder()
            .setBaseUrl("https://www.wanandroid.com/")
            .setIsLogcat(true)
            .setReadTimeOut(5000L)
            .setConnectTimeOut(5000L)
            .setHeaderHashMap(header)
            .addCodeKey("errorCode")
            .addMsgKey("errorMsg")
            .addDataKey("data")

            .addCodeKey("code")
            .addMsgKey("message")
            .addDataKey("data")

            .addSuccessCodeKey("0")
            .addSuccessCodeKey("200")
            .addSpecialCodeKey("103")
            .addSpecialCodeKey("104")
            .build()
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is SpecialCodeObservable&&arg!=null){
            val specialCodeBean=arg as SpecialCodeBean
            Toast.makeText(this,specialCodeBean.code,Toast.LENGTH_SHORT).show()
        }
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        GreySkinUtil.showGrey(activity)
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }



}