package com.example.ytnb.twotouchmail

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPickUp: Button = findViewById(R.id.button_pickup)
        btnPickUp.setOnClickListener{
            val intent = Intent(this,PickUpActivity::class.java)
            startActivity(intent)
        }
        val btnNoDinner: Button = findViewById(R.id.button_no_dinner)
        btnNoDinner.setOnClickListener{
            val intent = Intent(this,NoDinnerActivity::class.java)
            startActivity(intent)
        }
    }
}
