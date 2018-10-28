package com.example.ytnb.databinding1

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ytnb.databinding1.databinding.BookRowBinding

class BookAdapter(private val context: Context) : RecyclerView.Adapter<BookAdapter.BookHolder>() {
    var bookItems = listOf<Book>()
        set(value) {
            field = value
            notifyItemRangeInserted(0, field.size)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val binding: BookRowBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.book_row,
                parent,
                false
            )
        return BookHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookItems.size
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.binding.book = bookItems[position]
        holder.binding.executePendingBindings()
    }

    class BookHolder(val binding: BookRowBinding) : RecyclerView.ViewHolder(binding.root)
}

