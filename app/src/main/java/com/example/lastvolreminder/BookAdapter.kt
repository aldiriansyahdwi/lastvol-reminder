package com.example.lastvolreminder

import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lastvolreminder.bookdatabase.Book
import com.example.lastvolreminder.databinding.BookItemBinding

class BookAdapter(val listBook: List<Book>): RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    class ViewHolder(val binding: BookItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listBook.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(listBook[position]){
                binding.tvTitle.text = title
                binding.tvDescription.text = lastNumber.toString()
            }
        }
    }

}