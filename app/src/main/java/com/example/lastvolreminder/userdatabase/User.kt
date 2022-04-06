package com.example.lastvolreminder.userdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User (
    @PrimaryKey var email: String,
    @ColumnInfo(name = "username") var username : String,
    @ColumnInfo(name = "password") var password : String
)