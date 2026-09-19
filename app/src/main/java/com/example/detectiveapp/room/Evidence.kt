package com.example.detectiveapp.room

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey

@Entity(
    tableName = "evidence",
    foreignKeys = [
        ForeignKey(
            entity = cases::class,
            parentColumns = ["id"],
            childColumns = ["id_case"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Evidence(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val id_case: Int,

    val collection_date: String,

    val description: String,

    val location: String,

    val importance: String
)