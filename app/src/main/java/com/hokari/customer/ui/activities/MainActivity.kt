package com.hokari.customer.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hokari.customer.R
import com.hokari.customer.databinding.ActivityLoginBinding
import com.hokari.customer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}