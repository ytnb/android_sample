package com.example.ytnb.multiactivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        button1.setOnClickListener {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
        }
    }
}
