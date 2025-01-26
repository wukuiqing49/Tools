package com.wkq.net.util


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.io.IOException
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL
/**
 *
 *@Author: wkq
 *
 *@Time: 2025/1/23 12:19
 *
 *@Desc:  网络判断工具
 */
object NetworkUtils {

    /**
     * 判断是否有网络连接
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities != null && (
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    /**
     * 判断WIFI网络是否可用
     */
    fun isWifiConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            return networkInfo != null && networkInfo.isAvailable
        }
    }

    /**
     * 判断MOBILE网络是否可用
     */
    fun isMobileConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } else {
            val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            return networkInfo != null && networkInfo.isAvailable
        }
    }

    /**
     * 获取当前网络连接的类型信息
     */
    fun getConnectedType(context: Context): Int {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return when {
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> ConnectivityManager.TYPE_WIFI
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> ConnectivityManager.TYPE_MOBILE
                else -> -1
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.type ?: -1
        }
    }

    /**
     * 通过Ping网络的方式判断网络是否可用
     */
    fun isNetworkAvailableByPing(): Boolean {
        return try {
            val address = InetAddress.getByName("www.google.com")
            address.isReachable(3000)
        } catch (e: IOException) {
            false
        }
    }

    /**
     * 利用HttpURLConnection模拟请求判断网络是否可用
     */
    fun isNetworkAvailableByHttp(): Boolean {
        return try {
            val url = URL("http://www.google.com")
            val urlc = url.openConnection() as HttpURLConnection
            urlc.connectTimeout = 3000
            urlc.connect()
            urlc.responseCode == 200
        } catch (e: IOException) {
            false
        }
    }
}