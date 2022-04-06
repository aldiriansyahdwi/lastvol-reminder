package com.example.lastvolreminder

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User (
    @PrimaryKey var username : String,
    @ColumnInfo(name = "email") var email : String,
    @ColumnInfo(name = "password") var password : String
)