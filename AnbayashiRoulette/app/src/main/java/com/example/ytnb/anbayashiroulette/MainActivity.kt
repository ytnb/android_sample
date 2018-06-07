package com.example.ytnb.anbayashiroulette

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardList.setHasFixedSize(true)
        val llManager = LinearLayoutManager(this)
        llManager.orientation = LinearLayoutManager.VERTICAL
        cardList.layoutManager = llManager

        var anbayasi: ArrayList<AnbayasiData> = ArrayList<AnbayasiData>()
        for (i in MyData.commentArray.indices){
            anbayasi.add(AnbayasiData(
                    MyData.numberArray[i],
                    MyData.additionArray[i],
                    MyData.commentArray[i]
            ))
        }

        cardList.adapter = AnbayasiAdapter(anbayasi)
        cardList.smoothScrollToPosition(anbayasi.size - 1)
    }
}
