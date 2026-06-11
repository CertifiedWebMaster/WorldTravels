package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Query("SELECT * FROM cities ORDER BY dateVisited DESC")
    fun getAllCities(): Flow<List<City>>

    @Query("SELECT * FROM cities WHERE id = :id LIMIT 1")
    suspend fun getCityById(id: Int): City?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: City)

    @Update
    suspend fun updateCity(city: City)

    @Query("DELETE FROM cities WHERE id = :id")
    suspend fun deleteCityById(id: Int)
}
