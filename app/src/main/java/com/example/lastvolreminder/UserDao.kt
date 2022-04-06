package com.example.lastvolreminder

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface UserDao {
    @Query("SELECT * FROM User")
    fun getAllUser(): List<User>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUser(user: User): Long
}