package com.example.ytnb.listdetailsample.adapters

import android.support.v7.util.DiffUtil
import com.example.ytnb.listdetailsample.data.Item

class ItemDiffCallback: DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item?, newItem: Item?): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Item?, newItem: Item?): Boolean {
        return oldItem == newItem
    }
}