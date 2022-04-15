package com.example.lastvolreminder.repository

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lastvolreminder.BookAdapter
import com.example.lastvolreminder.bookdatabase.Book

class MainViewModel: ViewModel() {

    var localposts : List<Book>? = MainRepository().getData("aldi")

}
