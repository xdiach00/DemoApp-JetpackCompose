package com.xdiach.demoappcompose.ui.details

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdiach.model.PlacesRepository
import com.xdiach.model.response.place.PlaceDetailsResponse
import com.xdiach.model.response.places.PlacesListResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaceDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val repository: PlacesRepository = PlacesRepository()

    var placeState = mutableStateOf<PlaceDetailsResponse?>(null)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val placeId = savedStateHandle.get<String>("place_id") ?: ""
            placeState.value = repository.getPlaceDetails(placeId)
        }
    }
}

fun openGoogleMaps(latitude: Double, longitude: Double): Intent {
    // Create a Uri from an intent string. Use the result to create an Intent.
    val gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude)

    // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    // Make the Intent explicit by setting the Google Maps package
    mapIntent.setPackage("com.google.android.apps.maps")

    // Attempt to start an activity that can handle the Intent
    return mapIntent
}