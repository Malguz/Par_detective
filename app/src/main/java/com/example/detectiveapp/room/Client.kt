package com.example.detectiveapp.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "client",
    foreignKeys = [
        ForeignKey(
            entity = Case::class,
            parentColumns = ["id"],
            childColumns = ["id_case"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Client(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val id_case: Int,

    val name: String,

    val phone: String,

    val address: String
)