package com.example.lastvolreminder.repository

import androidx.lifecycle.MutableLiveData
import com.example.lastvolreminder.bookdatabase.Book
import com.example.lastvolreminder.bookdatabase.BookDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainRepository {
    private var bookDb: BookDatabase? = null
    var posts = MutableLiveData<List<Book>>()

    fun getData(user: String): List<Book>?{
        return bookDb?.bookDao()?.getUserBook(user)

    }
}