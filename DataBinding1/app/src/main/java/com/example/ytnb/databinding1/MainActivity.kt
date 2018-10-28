package com.example.ytnb.databinding1

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.ytnb.databinding1.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val adapter = BookAdapter(this).also { adapter ->
            adapter.bookItems = listOf(
                Book(0, "Android", Date()),
                Book(1, "Kotlin", Date()),
                Book(2, "Data", Date())
            )
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}
