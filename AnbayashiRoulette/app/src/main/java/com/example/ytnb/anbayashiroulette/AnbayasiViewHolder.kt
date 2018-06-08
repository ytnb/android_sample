package com.example.ytnb.anbayashiroulette

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class AnbayasiViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    var base: View = v
    var textViewNumber: TextView = v.findViewById(R.id.number2)
    var textViewComment: TextView = v.findViewById(R.id.comment2)
}