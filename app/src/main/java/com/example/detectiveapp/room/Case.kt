package com.example.detectiveapp.room

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "cases")
data class Case(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val description: String,

    val date: String,

    val status: String
)

