package com.example.detectiveapp.room

import androidx.room3.Entity
import androidx.room3.PrimaryKey
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val id_case: Int,

    val description: String,

    val date: String
)
