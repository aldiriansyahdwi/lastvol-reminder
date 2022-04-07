package com.example.lastvolreminder


import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lastvolreminder.bookdatabase.Book
import com.example.lastvolreminder.bookdatabase.BookDatabase
import com.example.lastvolreminder.databinding.BookItemBinding
import com.example.lastvolreminder.databinding.FragmentEditBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

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
                binding.ivComplete.isVisible = status
                binding.btnEdit.setOnClickListener {
                    val activity = it.context as MainActivity
                    val dialogFragment = EditFragment(listBook[position])
                    dialogFragment.show(activity.supportFragmentManager, null)
                }

                binding.btnDelete.setOnClickListener {
                    AlertDialog.Builder(it.context).setPositiveButton("Yes"){ p0, p1 ->
                        val bookDb = BookDatabase.getInstance(holder.itemView.context)

                        GlobalScope.async{
                            val result = bookDb?.bookDao()?.deleteBook(listBook[position])

                            (holder.itemView.context as MainActivity).runOnUiThread {
                                if (result!= 0)
                                    Toast.makeText(it.context, "Data \"${listBook[position].title}\" deleted", Toast.LENGTH_SHORT).show()
                                else
                                    Toast.makeText(it.context, "Data \"${listBook[position].title} \"failed to delete", Toast.LENGTH_SHORT).show()
                            }
                            (holder.itemView.context as ListFragment).fetchData()
                        }
                    }.setNegativeButton("Cancel"){
                        p0, p1 ->
                        p0.dismiss()
                    }.setMessage("Are you sure to delete \"${listBook[position].title}\" ?").setTitle("Delete Confirmation").create().show()
                }
            }

        }
    }

}