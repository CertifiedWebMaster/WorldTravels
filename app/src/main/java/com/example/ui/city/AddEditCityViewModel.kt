package com.example.ui.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.City
import com.example.data.CityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddEditCityUiState(
    val name: String = "",
    val country: String = "",
    val notes: String = "",
    val dateVisited: Long = System.currentTimeMillis(),
    val isSaving: Boolean = false,
    val saveCompleted: Boolean = false
)

class AddEditCityViewModel(
    private val cityId: Int,
    private val repository: CityRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddEditCityUiState())
    val uiState: StateFlow<AddEditCityUiState> = _uiState.asStateFlow()

    init {
        if (cityId > 0) {
            viewModelScope.launch {
                val city = repository.getCityById(cityId)
                if (city != null) {
                    _uiState.update {
                        it.copy(
                            name = city.name,
                            country = city.country,
                            notes = city.notes,
                            dateVisited = city.dateVisited
                        )
                    }
                }
            }
        }
    }

    fun updateName(name: String) = _uiState.update { it.copy(name = name) }
    fun updateCountry(country: String) = _uiState.update { it.copy(country = country) }
    fun updateNotes(notes: String) = _uiState.update { it.copy(notes = notes) }

    fun saveCity() {
        viewModelScope.launch {
            if (_uiState.value.name.isBlank()) return@launch

            _uiState.update { it.copy(isSaving = true) }
            val currentState = _uiState.value
            val city = City(
                id = if (cityId > 0) cityId else 0,
                name = currentState.name,
                country = currentState.country,
                notes = currentState.notes,
                dateVisited = currentState.dateVisited
            )
            if (cityId > 0) {
                repository.update(city)
            } else {
                repository.insert(city)
            }
            _uiState.update { it.copy(isSaving = false, saveCompleted = true) }
        }
    }
}

fun provideAddEditCityViewModelFactory(cityId: Int, repository: CityRepository): ViewModelProvider.Factory =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AddEditCityViewModel(cityId, repository) as T
        }
    }
