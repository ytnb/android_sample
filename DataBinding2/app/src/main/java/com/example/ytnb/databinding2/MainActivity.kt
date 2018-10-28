package com.example.ytnb.databinding2

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ytnb.databinding2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val form = MainForm()

        form.onComplete.observe(this, Observer {
            Toast.makeText(this, "送信しました。", Toast.LENGTH_SHORT).show()
            finish()
        })

        binding.form = form

    }
}
