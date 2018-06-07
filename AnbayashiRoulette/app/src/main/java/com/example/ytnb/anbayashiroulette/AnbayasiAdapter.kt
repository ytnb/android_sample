package com.example.ytnb.anbayashiroulette

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class AnbayasiAdapter(val roulette: ArrayList<AnbayasiData>) : RecyclerView.Adapter<AnbayasiViewHolder>() {
    val rouletteDataSet: ArrayList<AnbayasiData> = roulette

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnbayasiViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.cards_layout2, parent, false)
        return AnbayasiViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnbayasiViewHolder, listPosition: Int) {
        holder.textViewNumber.text = "${rouletteDataSet[listPosition].number}本"
        holder.textViewComment.text = rouletteDataSet[listPosition].comment
        holder.base.setOnClickListener(View.OnClickListener {
            Toast.makeText(it.context, "おまけ${rouletteDataSet[listPosition].addition}本", Toast.LENGTH_SHORT).show()
        })

    }

    override fun getItemCount(): Int {
        return rouletteDataSet.size
    }
}