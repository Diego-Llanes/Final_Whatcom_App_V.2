package com.example.whatcomapp.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache_table")

data class CacheEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "service_name")
    val foodBankName: String = "",

    @ColumnInfo(name = "address")
    val adress: String = "",

    @ColumnInfo(name = "phone_number")
    var phoneNumber: String = "",

    @ColumnInfo(name = "email")
    var email: String = "",

    @ColumnInfo(name = "website")
    var website: String = "",

    @ColumnInfo(name = "days")
    var days: String = "",

    @ColumnInfo(name = "additional_notes")
    var aditionalNotes: String = "",

    @ColumnInfo(name = "service_type")
    var serviceType: String = "",

    )