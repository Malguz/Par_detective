package com.example.detectiveapp.room

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "evidence")
data class Evidence(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val id_case: Int,

    val collection_date: String,

    val description: String,

    val location: String,

    val importance: String
)