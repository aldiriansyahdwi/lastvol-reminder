package com.example.lastvolreminder.bookdatabase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
class Book(
    @PrimaryKey var title : String,
    @ColumnInfo(name = "user") var user : String?,
    @ColumnInfo(name = "lastNumber") var lastNumber : Int,
    @ColumnInfo(name = "status") var status: Boolean
): Parcelable