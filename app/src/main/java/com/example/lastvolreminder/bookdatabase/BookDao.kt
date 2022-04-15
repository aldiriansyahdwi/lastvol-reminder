package com.example.lastvolreminder.bookdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface BookDao {
    @Query ("SELECT * FROM Book WHERE user LIKE:username")
    fun getUserBook(username : String): List<Book>

    @Insert (onConflict = REPLACE)
    fun insertUserBook(book: Book): Long

    @Update
    fun updateBook(book: Book): Int

    @Delete
    fun deleteBook(book: Book): Int
}