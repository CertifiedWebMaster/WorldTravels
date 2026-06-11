package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.CityRepository

object Graph {
    lateinit var database: AppDatabase
        private set
    
    val repository: CityRepository by lazy {
        CityRepository(database.cityDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, AppDatabase::class.java, "whereihavebeen.db")
            .build()
    }
}
