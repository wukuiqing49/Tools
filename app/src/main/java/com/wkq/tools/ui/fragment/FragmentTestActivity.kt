package com.wkq.tools.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.wkq.tools.R
import com.wkq.tools.databinding.ActivityTestFragmentBinding
import com.wkq.tools.databinding.ActivityUiBinding

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/2/11 15:10
 *
 *@Desc:
 */
class FragmentTestActivity : AppCompatActivity() {

    companion object{
        fun startActivity(context:Context){
            context.startActivity(Intent(context, FragmentTestActivity::class.java))

        }
    }
    val binding: ActivityTestFragmentBinding by lazy {
        ActivityTestFragmentBinding.inflate(LayoutInflater.from(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btFinish.setOnClickListener { finish() }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_layout,TestFragment()).commit()

    }


}