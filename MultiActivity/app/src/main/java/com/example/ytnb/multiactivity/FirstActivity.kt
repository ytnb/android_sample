package com.example.ytnb.multiactivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val btnNext: Button = findViewById(R.id.button1)
        btnNext.setOnClickListener {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
        }
    }
}
