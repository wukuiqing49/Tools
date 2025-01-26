package com.wkq.tools.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.wkq.net.DataCallback
import com.wkq.net.FlowHelper
import com.wkq.net.NetWork
import com.wkq.net.model.BaseInfo
import com.wkq.net.model.FlowThreeZipBean
import com.wkq.net.model.ResultInfo
import com.wkq.tools.bean.Banner
import com.wkq.net.model.FlowTwoZipBean
import com.wkq.net.util.FlowZipUtils
import com.wkq.tools.bean.UserInfo
import com.wkq.tools.databinding.ActivityNetBinding
import com.wkq.tools.net.Api
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/2 17:39
 *
 */
class NetActivity : AppCompatActivity() {
    lateinit var binding: ActivityNetBinding

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, NetActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()

        lifecycle.addObserver(object :DefaultLifecycleObserver{
            override fun onCreate(owner: LifecycleOwner) {
                Log.e("生命周期:", "onCreate")
            }

            override fun onResume(owner: LifecycleOwner) {
                Log.e("生命周期:", "onResume")
            }

            override fun onPause(owner: LifecycleOwner) {
                Log.e("生命周期:", "onPause")
            }

            override fun onStart(owner: LifecycleOwner) {
                Log.e("生命周期:", "onStart")
            }

            override fun onStop(owner: LifecycleOwner) {

                Log.e("生命周期:", "onStop")
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)

                Log.e("生命周期:", "onDestroy")
            }
        })
    }

    private fun initView() {
        binding.btFGet.setOnClickListener { fGet() }
        binding.btFPost.setOnClickListener { fPost() }
        binding.btPGet.setOnClickListener { pGet() }
        binding.btPPost.setOnClickListener { pPost() }
        binding.btFinish.setOnClickListener { finish() }
        binding.btSerial.setOnClickListener { serial() }
        binding.btParallel.setOnClickListener { parallel() }

        binding.btFSerial.setOnClickListener { fSerial() }
        binding.btFParallel.setOnClickListener { fParallel() }
        binding.btParallelM1.setOnClickListener { singleParallelM1() }
        binding.btParallelM2.setOnClickListener { singleParallelM2() }
        binding.btParallelM3.setOnClickListener { singleParallelM3() }

        binding.btParallelHbM1.setOnClickListener { mergeParallelHbM1() }
        binding.btParallelHbM2.setOnClickListener { mergeParallelHbM2() }


        binding.btCxDd.setOnClickListener { serialNet() }
        binding.btCxHb.setOnClickListener { serialNet() }
        binding.btCxZd.setOnClickListener { serialNet3() }


    }


    fun pGet() {
        lifecycleScope.launch {
            val hashMap = HashMap<String, String>()
            hashMap.put("test", "1111")
            val result = NetWork.getInstance().create(Api::class.java).banner(hashMap)
            if (result.isSuccess) {
                binding.tvContent.setText(result.data.get(0).title)
            }
        }
    }

    fun pPost() {
        lifecycleScope.launch {
            val hashMap = HashMap<String, String>()
            hashMap.put("username", "wkq")
            hashMap.put("password", "123456")
            val result = NetWork.getInstance().create(Api::class.java).login(hashMap)

            if (result.isSuccess) {
                binding.tvContent.setText(result.data.username)
            }
        }


    }

    fun fPost() {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq111")
        hashMap.put("password", "123456")
        MainScope().launch {
            val result1 = suspend { NetWork.getInstance().create(Api::class.java).banner(hashMap) }
            val job = FlowHelper.getInstance().request<List<Banner>>(
                { NetWork.getInstance().create(Api::class.java).banner(hashMap) },
                object : DataCallback<List<Banner>> {
                    override fun onSuccess(t: List<Banner>) {
                        Toast.makeText(this@NetActivity, t.size.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onFail(code: String, msg: String) {
                        Toast.makeText(this@NetActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    fun fGet() {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")
        val job = FlowHelper.getInstance()
            .request<UserInfo>({ NetWork.getInstance().create(Api::class.java).login(hashMap) },
                object : DataCallback<UserInfo> {
                    override fun onSuccess(t: UserInfo) {
                        Toast.makeText(this@NetActivity, t.username, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFail(code: String, msg: String) {
                        Toast.makeText(this@NetActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                })
    }

    /**
     * 串行请求
     */
    fun fSerial() {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")

        FlowHelper.getInstance().requestFlowSerial(object : DataCallback<ResultInfo<*>> {
            override fun onSuccess(t: ResultInfo<*>) {
                Toast.makeText(this@NetActivity, t.code, Toast.LENGTH_SHORT).show()
            }

            override fun onFail(code: String, msg: String) {
                Toast.makeText(this@NetActivity, msg, Toast.LENGTH_SHORT).show()
            }
        }, {
            delay(3000)
            NetWork.getInstance().create(Api::class.java).login(hashMap)
        }, { NetWork.getInstance().create(Api::class.java).banner(hashMap) })


    }

    fun fParallel() {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")
        lifecycleScope.launch {
            flow<FlowTwoZipBean<UserInfo, List<Banner>>> {
                val result1 = async {
                    NetWork.getInstance().create(Api::class.java).banner(hashMap)
                }
                val result2 = async {
                    NetWork.getInstance().create(Api::class.java).login(hashMap)
                }
                val zipBean = FlowTwoZipBean<UserInfo, List<Banner>>(
                    result2.await().data, result1.await().data
                )
                emit(zipBean)
            }.collect {
//            println(it.user.username)
                Toast.makeText(this@NetActivity, it.first.username, Toast.LENGTH_SHORT).show()
            }
        }
    }


    /**
     * 串行请求
     */
    fun serial() {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")
        lifecycleScope.launch {
            flow<FlowTwoZipBean<UserInfo, List<Banner>>> {
                val result1 = NetWork.getInstance().create(Api::class.java).banner(hashMap)
                val result2 = NetWork.getInstance().create(Api::class.java).login(hashMap)
                val zipBean = FlowTwoZipBean<UserInfo, List<Banner>>(result2.data, result1.data)
                emit(zipBean)
            }.collect {
                Toast.makeText(this@NetActivity, it.first.username, Toast.LENGTH_SHORT).show()
            }
        }

    }

    /**
     * 并行网络请求 单独返回数据
     */
    fun parallel() {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")


        //方式一
        lifecycleScope.launch {
            FlowHelper.getInstance().requestSingleParallel(object : DataCallback<ResultInfo<*>> {
                override fun onSuccess(t: ResultInfo<*>) {
                    Toast.makeText(this@NetActivity, t.code, Toast.LENGTH_SHORT).show()
                }

                override fun onFail(code: String, msg: String) {
                    Toast.makeText(this@NetActivity, msg, Toast.LENGTH_SHORT).show()
                }
            }, {
                delay(3000)
                NetWork.getInstance().create(Api::class.java).login(hashMap)
            }, { NetWork.getInstance().create(Api::class.java).banner(hashMap) })

        }
        //方式二
        lifecycleScope.launch {

            val functionList: MutableList<suspend () -> Any> = mutableListOf()
            functionList.add({
                delay(3000)
                NetWork.getInstance().create(Api::class.java).login(hashMap)
            })
            functionList.add({ NetWork.getInstance().create(Api::class.java).banner(hashMap) })

            FlowHelper.getInstance()
                .requestSingleParallel(functionList, object : DataCallback<ResultInfo<*>> {
                    override fun onSuccess(t: ResultInfo<*>) {
                        Toast.makeText(this@NetActivity, t.code, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFail(code: String, msg: String) {
                        Toast.makeText(this@NetActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    fun singleParallelM1() {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")

        FlowHelper.getInstance().requestSingleParallel(object : DataCallback<ResultInfo<*>> {
            override fun onSuccess(t: ResultInfo<*>) {
                Toast.makeText(this@NetActivity, t.code, Toast.LENGTH_SHORT).show()
            }

            override fun onFail(code: String, msg: String) {
                Toast.makeText(this@NetActivity, msg, Toast.LENGTH_SHORT).show()
            }
        }, {
            delay(3000)
            NetWork.getInstance().create(Api::class.java).login(hashMap)
        }, { NetWork.getInstance().create(Api::class.java).banner(hashMap) })
    }

    fun singleParallelM2() {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")

        val functionList: MutableList<suspend () -> Any> = mutableListOf()
        functionList.add({
            delay(3000)
            NetWork.getInstance().create(Api::class.java).login(hashMap)
        })
        functionList.add({ NetWork.getInstance().create(Api::class.java).banner(hashMap) })

        FlowHelper.getInstance()
            .requestSingleParallel(functionList, object : DataCallback<ResultInfo<*>> {
                override fun onSuccess(t: ResultInfo<*>) {
                    Toast.makeText(this@NetActivity, t.code, Toast.LENGTH_SHORT).show()
                }

                override fun onFail(code: String, msg: String) {
                    Toast.makeText(this@NetActivity, msg, Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun singleParallelM3() {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")

        val functionList: MutableList<suspend () -> Any> = mutableListOf()
        functionList.add({
            delay(3000)
            NetWork.getInstance().create(Api::class.java).login(hashMap)
        })
        functionList.add({ NetWork.getInstance().create(Api::class.java).banner(hashMap) })
        lifecycleScope.launch {
            functionList.asFlow().flatMapMerge {
                FlowZipUtils.getFlow { it() }
            }.collect{
                Toast.makeText(this@NetActivity, "成功", Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun mergeParallelHbM1() {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")
        val result1 = suspend {
            delay(3000)
            NetWork.getInstance().create(Api::class.java).banner(hashMap)
        }
        val result2 = suspend {
            NetWork.getInstance().create(Api::class.java).login(hashMap)
        }
        FlowHelper.getInstance()
            .requestMergeFlowParallel<ResultInfo<List<Banner>>, ResultInfo<UserInfo>>(result1,
                result2,
                object : DataCallback<FlowTwoZipBean<ResultInfo<List<Banner>>, ResultInfo<UserInfo>>> {
                    override fun onSuccess(
                        t: FlowTwoZipBean<ResultInfo<List<Banner>>, ResultInfo<UserInfo>>
                    ) {
                        Toast.makeText(this@NetActivity, "成功", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFail(code: String, msg: String) {
                        Toast.makeText(this@NetActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                })


    }

    fun mergeParallelHbM2() {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")
        val result1 = suspend {
            delay(3000)
            NetWork.getInstance().create(Api::class.java).banner(hashMap)
        }
        val result2 = suspend {
            NetWork.getInstance().create(Api::class.java).login(hashMap)
        }
        val result3 = suspend {
            NetWork.getInstance().create(Api::class.java).login2(hashMap)
        }
        FlowHelper.getInstance()
            .requestMergeFlowParallel<ResultInfo<List<Banner>>, ResultInfo<UserInfo>, ResultInfo<UserInfo>>(
                result1, result2, result3,
                object : DataCallback<FlowThreeZipBean<ResultInfo<List<Banner>>, ResultInfo<UserInfo>, ResultInfo<UserInfo>>> {
                    override fun onSuccess(
                        t: FlowThreeZipBean<ResultInfo<List<Banner>>, ResultInfo<UserInfo>, ResultInfo<UserInfo>>
                    ) {
                        Toast.makeText(this@NetActivity, "成功", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFail(code: String, msg: String) {
                        Toast.makeText(this@NetActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                })


    }


    fun serialNet() {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")
        //方式1
        lifecycleScope.launch {
            val result1 = NetWork.getInstance().create(Api::class.java).login(hashMap)
            Toast.makeText(this@NetActivity, "成功了result1", Toast.LENGTH_SHORT).show()
            val result2 = NetWork.getInstance().create(Api::class.java).banner(hashMap)
            Toast.makeText(this@NetActivity, "成功了result2", Toast.LENGTH_SHORT).show()

        }


        // 方式2
        val functionList: MutableList<suspend () -> BaseInfo> = mutableListOf()
        functionList.add({
            delay(3000)
            NetWork.getInstance().create(Api::class.java).login(hashMap)
        })
        functionList.add({
            NetWork.getInstance().create(Api::class.java).banner(hashMap)
        })


        FlowHelper.getInstance()
            .requestFlowSerial<BaseInfo>(functionList, object : DataCallback<BaseInfo> {
                override fun onSuccess(t: BaseInfo) {
                    Toast.makeText(this@NetActivity, "成功了", Toast.LENGTH_SHORT).show()
                }

                override fun onFail(code: String, msg: String) {
                    Toast.makeText(this@NetActivity, msg, Toast.LENGTH_SHORT).show()
                }
            })

        // 方式3
        lifecycleScope.launch {
            flow {
                emit(NetWork.getInstance().create(Api::class.java).login(hashMap))
            }.flatMapConcat {
                FlowZipUtils.getFlow {
                    NetWork.getInstance().create(Api::class.java).banner(hashMap)
                }
            }.collect {

            }
        }


    }


    fun serialNet2() {

    }

    //串行 保证顺序执行 flatMapConcat  串行执行  flatMapMerge 并行直行
    fun serialNet3() {
        val hashMap = HashMap<String, String>()
        hashMap.put("username", "wkq")
        hashMap.put("password", "123456")
        lifecycleScope.launch {

            FlowZipUtils.getFlow { NetWork.getInstance().create(Api::class.java).login(hashMap) }

                .flatMapConcat {
                    FlowZipUtils.getFlow {
                        NetWork.getInstance().create(Api::class.java).login(hashMap)
                        throw RuntimeException("中断串行请求")
                    }
                }.flatMapConcat {
                    FlowZipUtils.getFlow {
                        NetWork.getInstance().create(Api::class.java).login(hashMap)
                    }
                }.catch {
                    Toast.makeText(this@NetActivity, it.message, Toast.LENGTH_SHORT).show()
                }
                .collect {
                    Toast.makeText(this@NetActivity, "成功了", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }


}