package com.xdiach.demoappcompose.ui.places

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdiach.demoappcompose.ui.countDistance
import com.xdiach.model.PlacesRepository
import com.xdiach.model.response.places.PlacesListResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

var sortingType by mutableStateOf(true)

class PlacesListViewModel(private val repository: PlacesRepository = PlacesRepository()) :
    ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val places = getPlaces()
                placesState.value = places
            }.onFailure {
                Log.e("List", it.message, it)
            }
        }
    }

    val placesState: MutableState<List<PlacesListResponseItem>> =
        mutableStateOf(emptyList<PlacesListResponseItem>())

    private suspend fun getPlaces(): List<PlacesListResponseItem> {
        return repository.getPlaces()
    }
}