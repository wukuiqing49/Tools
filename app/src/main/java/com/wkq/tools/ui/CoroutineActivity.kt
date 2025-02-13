package com.wkq.tools.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.wkq.tools.databinding.ActivityTestBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


/**
 *@Desc: 协程 学习
 *
 *@Author: wkq
 *
 *@Time: 2025/1/2 17:39
 *
 */
class CoroutineActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestBinding

    class TestViewModel:ViewModel(){
        // LiveData
         val liveData = MutableLiveData<String>()
        //数据更改
        fun update(updateStr:String) {
            liveData.value="数据更改了:"+updateStr
        }
    }

   //创建方式<一>
    val model=ViewModelProvider.create(this).get(TestViewModel::class.java)
    //创建方式<二>
    private val viewModel: TestViewModel by viewModels()
    //创建方式<三>
    private val netViewModel by lazy { TestViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        //注册监听
        viewModel.liveData.observe(this){
            print("结果:"+it)
        }
        //更新数据
        viewModel.update("我更新了")





        startCoroutine()
        val lunchJob1 = GlobalScope.launch (Dispatchers.IO){
            Log.e("日志顺序:", "launch")
            delay(2000)
            "launch启动最后一行"
        }

        val lunchJob2 = MainScope().launch {
            Log.e("日志顺序:", "launch")
            delay(2000)
            "launch启动最后一行"
        }


        val lunchJob3 = lifecycleScope.launch {
            Log.e("日志顺序:", "launch")
            delay(2000)
            "launch启动最后一行"
        }

    }



    /**
     * 启动协程
     *
     */
    fun startCoroutine() {

//        runBlocking：T 启动一个新的协程并阻塞调用它的线程，直到里面的代码执行完毕,返回值是泛型T，就是你协程体中最后一行是什么类型，最终返回的是什么类型T就是什么类型。
//        launch  启动一个协程但不会阻塞调用线程,必须要在协程作用域(CoroutineScope)中才能调用,返回值是一个Job。
//        async  也会启动一个新的协程，并返回一个 Deferred 接口实现，这个接口其实也继承了Job 接口，可以使用 await 挂起函数等待返回结果。
//        启动一个协程但不会阻塞调用线程,必须要在协程作用域(CoroutineScope)中才能调用。以Deferred对象的形式返回协程任务。返回值泛型T同runBlocking类似都是协程体最后一行的类型。

        val block = runBlocking {
            delay(3000)
            Log.e("日志顺序:", "runBlocking")
            delay(3000)
            "block结果"
        }
        Log.e("日志顺序:", "1111111111111111111111111111111")
        Log.e("日志顺序:", "结果:" + block)

        val lunchJob = GlobalScope.launch (Dispatchers.IO){
            Log.e("日志顺序:", "launch")
            delay(2000)
            "launch启动最后一行"
        }
        Log.e("日志顺序:", "结果:" + lunchJob)

        val syncDeferred = GlobalScope.async {
            Log.e("日志顺序:", "async")
            delay(2000)
            "Deferred启动最后一行"
        }
        Log.e("日志顺序:", "结果:" + syncDeferred)


        GlobalScope.launch {
          val syncAwait=  async {
                Log.e("日志顺序:", "asyncAwait启动:")
              delay(2000)
                "syncAwait启动最后一行"
            }
            Log.e("日志顺序:", "结果:" + syncAwait.await())
        }

    }

    /**
     * 调度器
     */
    fun coroutineDispatcher(){
        //  CoroutineDispatcher 调度器
        // 调度器它确定了相关的协程在哪个线程或哪些线程上执行。协程调度器可以将协程限制在一个特定的线程执行，或将它分派到一个线程池，亦或是让它不受限地运行。

        //执行的线程
        GlobalScope.launch (Dispatchers.Default){  } // CPU密集型任务调度器，适合处理后台计算  比如：Json的解析，数据计算等
        GlobalScope.launch (Dispatchers.IO){  } // IO调度器，，IO密集型任务调度器，适合执行IO相关操作。比如：网络处理，数据库操作，文件操作等
        GlobalScope.launch (Dispatchers.Main){  } // UI调度器， 即在主线程上执行，通常用于UI交互，刷新等
        GlobalScope.launch (Dispatchers.Unconfined){  } // 非受限调度器，又或者称为“无所谓”调度器，不要求协程执行在特定线程上。

        // 协程中切换线程 withContext
        GlobalScope.launch (Dispatchers.Unconfined){

            val  result=withContext(Dispatchers.Main){
                33
                "44"
            }
        }
    }


    /**
     * 协程上下文
     */

    fun coroutineContext(){

    }

}