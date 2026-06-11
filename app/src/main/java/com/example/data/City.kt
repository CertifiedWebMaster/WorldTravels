package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class City(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val country: String = "",
    val dateVisited: Long,
    val notes: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null
)
