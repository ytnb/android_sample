package com.example.ytnb.twotouchmail

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_pickup.setOnClickListener{
            val intent = Intent(this,PickUpActivity::class.java)
            startActivity(intent)
        }
        button_no_dinner.setOnClickListener{
            val intent = Intent(this,NoDinnerActivity::class.java)
            startActivity(intent)
        }
    }
}
