package com.wkq.tools.decrypt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wkq.tools.databinding.ActivityDecryptBinding
import com.wkq.tools.ui.ColorUIGradientActivity

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/7/11 15:38
 *
 *@Desc:
 */
class DecryptActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, DecryptActivity::class.java))
        }
    }

    val binding: ActivityDecryptBinding by lazy {
        ActivityDecryptBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val originalString = "wwwwtttt1111"
        val xorKey = byteArrayOf(0x55, 0x23, 0x66, 0x13)
        binding.btFinish.setOnClickListener { finish() }
        binding.tvEncryption.setOnClickListener {
            //字符串转为 byte[]
            val encryptData = EncryptUtils.encrypt(originalString, xorKey)
            val prnStr = EncryptUtils.toCPlusPlusFormat(encryptData, "加密数据")
            binding.tvContent.text = prnStr
        }
        binding.tvDecrypt.setOnClickListener {
            binding.tvContent.text = DecryptUtil.getDecryptedStr()
        }

    }
}