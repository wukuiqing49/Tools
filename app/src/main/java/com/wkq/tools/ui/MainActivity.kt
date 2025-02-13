package com.wkq.tools.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wkq.tools.databinding.ActivityMainBinding
import com.wkq.ui.util.SharedPreferencesHelper

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/2/11 15:10
 *
 *@Desc:
 */
class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btUI.setOnClickListener {
            UIActivity.startActivity(this)
        }
        binding.btGray.setOnClickListener {
            val isGrey = SharedPreferencesHelper.getInstance(this).getBoolean("isGrey", false)
            SharedPreferencesHelper.getInstance(this).setValue("isGrey", !isGrey)
            Toast.makeText(this,"重启生效",Toast.LENGTH_SHORT).show()
        }

    }
}