package com.example.data

import kotlinx.coroutines.flow.Flow

class CityRepository(private val cityDao: CityDao) {
    val allCities: Flow<List<City>> = cityDao.getAllCities()

    suspend fun getCityById(id: Int): City? = cityDao.getCityById(id)

    suspend fun insert(city: City) = cityDao.insertCity(city)
    
    suspend fun update(city: City) = cityDao.updateCity(city)

    suspend fun deleteById(id: Int) = cityDao.deleteCityById(id)
}
