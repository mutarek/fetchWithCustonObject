package com.bss.firebase_with_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bss.firebase_with_kotlin.databinding.ActivityMyProductBinding

class MyProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
    }

    private fun getIntentData() {
        val name = intent.getStringExtra("code")
        binding.productRoot.text = name
    }
}