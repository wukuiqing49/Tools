package com.wkq.tools.ui

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.wkq.net.DataCallback
import com.wkq.net.FlowHelper
import com.wkq.net.NetConstant
import com.wkq.net.NetWork
import com.wkq.net.model.ResultInfo
import com.wkq.tools.TestView
import com.wkq.tools.bean.UserInfo
import com.wkq.tools.databinding.ActivityTestBinding
import com.wkq.tools.net.Api
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import util.MediaPlayerListener
import util.MediaPlayerUtil
import util.OnlineMediaPlayerUtil

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/2 17:39
 *
 */
class TestActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestBinding

    val mainScope = MainScope()
    private val netViewModel by lazy {
        TestView()
    }
    val test = MutableLiveData<Int>()
    val B = MutableLiveData<Int>()
    val C = MutableLiveData<Int>()
    val D = MutableLiveData<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        netViewModel.resultMutableLiveData.observe(this) {

        }
// 初始化工具类
        OnlineMediaPlayerUtil.getInstance().init(this);

        // 播放音频并开启震动

        // 停止播放
        // OnlineMediaPlayerUtil.getInstance().stopAudio();

        // 检查是否正在播放
        val colorInt = ContextCompat.getColor(this, R.color.holo_purple)

        // 将整数颜色值转换为十六进制字符串
//        val colorHex = String.format("#%08X", (-0x1 and colorInt))
//        binding.btNet.setText(colorHex)
        binding.btNet.setOnClickListener {
            NetActivity.startActivity(this)
//            playRawUri()
//            OnlineMediaPlayerUtil.getInstance().init(this);
//            if (!OnlineMediaPlayerUtil.getInstance().isPlaying()) {
//                // 处理逻辑
//
//                // 自定义震动模式和强度
//                val pattern = longArrayOf(0, 100, 50, 100, 50, 100)
//                val amplitudes = intArrayOf(255, 255, 255, 255, 255, 255)
//                OnlineMediaPlayerUtil.getInstance().playAudio(path, true,pattern,amplitudes);
//
//            }
        }
        test.observe(this) {

        }

        B.observe(this) {

        }

        C.observe(this) {

        }
        C.observe(this) {

        }
        test.value=1
        test.value=1



        binding.btCallback.setOnClickListener {
            val hashMap = HashMap<String, String>()
            hashMap.put("username", "wkq111")
            hashMap.put("password", "123456")

          val job=  FlowHelper.getInstance()
                .request<UserInfo>({ NetWork.getInstance().create(Api::class.java).login(hashMap) },
                    object : DataCallback<UserInfo> {
                        override fun onSuccess(t: UserInfo) {
                            println("map collect ")
                        }

                        override fun onFail(code: String, msg: String) {
                            println("map collect ")
                        }
                    })
//            job?.cancel()
        }



        binding.btGet.setOnClickListener {

//            test()
            val data = runBlocking {
                val hashMap = HashMap<String, String>()
                hashMap.put("username", "wkq")
                hashMap.put("password", "123456")
                NetWork.getInstance().create(Api::class.java).login(hashMap)
            }


        }

        binding.btPost.setOnClickListener {

            testPost()

        }

        binding.btOtherBase.setOnClickListener {
//            otherBase()
            val hashMap = HashMap<String, String>()
            hashMap.put("username", "wkq")
            hashMap.put("password", "123456")
            netViewModel.testRequest(hashMap)
        }

    }

    fun getUser(id: Int): Flow<ResultInfo<UserInfo>> = flow {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")
        val result = NetWork.getInstance().create(Api::class.java).login(hashMap)
        emit(result)
    }

    private fun testPost() {

        lifecycleScope.launch {
            val hashMap = HashMap<String, String>()
            hashMap.put("username", "wkq")
            hashMap.put("password", "123456")
            var result = NetWork.getInstance().create(Api::class.java).login(hashMap)

            binding.btPost.setText(result.code)

            Log.e("", "")

        }

    }

    private fun otherBase() {

        lifecycleScope.launch {
            val hashMap = HashMap<String, String>()
            hashMap.put("username", "wkq")
            hashMap.put("password", "123456")
            var result = NetWork.getInstance().create(Api::class.java, NetConstant.BASE_URL)
                .list_type(hashMap)

            binding.btPost.setText(result.msg)

            Log.e("", "")

        }

    }

    private fun test() {
        lifecycleScope.launch {
            val hashMap = HashMap<String, String>()
            hashMap.put("test", "1111")
            val result = NetWork.getInstance().create(Api::class.java).banner(hashMap)
            if (result.isSuccess) {
                binding.btPost.setText(result.data.get(0).title)
            }

        }

    }


    var path="https://downsc.chinaz.net/Files/DownLoad/sound1/202103/s1024.mp3"
    private fun playRawUri() {
        MediaPlayerUtil.initMedia(this,path)

        MediaPlayerUtil.setMediaListener(object : MediaPlayerListener {
            override fun onErr(messageCode: Int) {
            }

            override fun finish() {
            }
            override fun prepare() {
                MediaPlayerUtil.playMedia()
            }
        })
        MediaPlayerUtil.prepareAsync()
    }



    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
        if (mainScope != null) mainScope.cancel()

    }

}