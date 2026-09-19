package com.example.detectiveapp.room

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "client")
data class Client(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val id_case: Int,

    val name: String,

    val phone: String,

    val address: String
)