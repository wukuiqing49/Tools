package com.wkq.tools

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2025/1/15 15:17
 *
 */
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.wkq.tools.databinding.ActivityTestBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FlowActivity : AppCompatActivity() {

    lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.btGet.setOnClickListener({
            // 启动异步任务
            startAsyncTasks()
        })

    }

    override fun onDestroy() {
        super.onDestroy()

        // 清理资源
        MainScope().cancel()
    }

    /**
     * 舒徐执行
     */
    private fun startAsyncTasks() {

        lifecycleScope.launch {

           val da= withContext(Dispatchers.IO){
               // 同时执行任务 a 和任务 b
               val resultA = async { taskA() }
               Log.e("执行顺序:", resultA.await())

               val resultB = async { taskB() }
               val resultC = async { taskC() }
               val resultD = async { taskD() }
               Log.e("执行顺序:", resultB.await())

               Log.e("执行顺序:", resultC.await())
               Log.e("执行顺序:", resultD.await())
           }

            // 执行任务 e
            taskE()
        }
    }

    private suspend fun taskA(): String {
        // 执行任务 a 的逻辑
        delay(5000) // 模拟耗时操作，例如网络请求或计算
        return "Task A 完成"
    }

    private suspend fun taskB(): String {
        // 执行任务 b 的逻辑
        delay(1000)
        return "Task B 完成"
    }

    private suspend fun taskC(): String {
        // 执行任务 c 的逻辑
        delay(3000)
        return "Task C 完成"
    }

    private suspend fun taskD(): String {
        // 执行任务 d 的逻辑
        delay(1000)
        return "Task D 完成"
    }

    private fun taskE() {
        // 执行任务 e 的逻辑
    }
}

