package com.wkq.tools.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.wkq.tools.databinding.ActivityUiBinding
import com.wkq.tools.decrypt.DecryptActivity
import com.wkq.tools.ui.fragment.FragmentTestActivity

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/2/11 15:10
 *
 *@Desc:
 */
class UIActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, UIActivity::class.java))

        }
    }

    val binding: ActivityUiBinding by lazy {
        ActivityUiBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btFinish.setOnClickListener { finish() }
        binding.btTextview.setOnClickListener {
            TextViewUIActivity.startActivity(this)
        }
        binding.btSpan.setOnClickListener {
            UISpanActivity.startActivity(this)
        }
        binding.btFragment.setOnClickListener {
            FragmentTestActivity.startActivity(this)
        }

        binding.btGradient.setOnClickListener {
            ColorGradientActivity.startActivity(this)
        }
        binding.btGradientUi.setOnClickListener {
            ColorUIGradientActivity.startActivity(this)
        }
 binding.btSo.setOnClickListener {
     DecryptActivity.startActivity(this)
        }
        binding.btShape.setOnClickListener {
            ShapeDrawableActivity.startActivity(this)
        }

    }


}