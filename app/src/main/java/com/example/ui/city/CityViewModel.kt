package com.example.ui.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.City
import com.example.data.CityRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CityViewModel(private val repository: CityRepository) : ViewModel() {
    val uiState: StateFlow<List<City>> = repository.allCities
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteCity(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}

fun provideCityViewModelFactory(repository: CityRepository): ViewModelProvider.Factory =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CityViewModel(repository) as T
        }
    }
