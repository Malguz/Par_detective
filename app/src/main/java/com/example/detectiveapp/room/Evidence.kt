package com.example.detectiveapp.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "evidence",
    foreignKeys = [
        ForeignKey(
            entity = Case::class,
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