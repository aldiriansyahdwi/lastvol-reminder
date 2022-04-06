package com.example.lastvolreminder.bookdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Book(
    @PrimaryKey(autoGenerate = true) var id : Int,
    @ColumnInfo(name = "user") var user : String,
    @ColumnInfo(name = "title") var title : String,
    @ColumnInfo(name = "lastNumber") var lastNumber : Int,
    @ColumnInfo(name = "lastTitle") var lastTitle: String,
    @ColumnInfo(name = "status") var status: Boolean
)