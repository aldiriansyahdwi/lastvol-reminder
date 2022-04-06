package com.example.lastvolreminder.userdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAllUser(): List<User>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUser(user: User): Long

    @Query("SELECT * FROM User WHERE email LIKE :email AND password LIKE :password")
    fun checkLogin(email: String, password: String): List<User>
}
