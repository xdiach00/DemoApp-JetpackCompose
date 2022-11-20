package com.xdiach.model

import com.xdiach.model.api.PlacesWebService
import com.xdiach.model.response.place.PlaceDetailsResponse
import com.xdiach.model.response.places.PlacesListResponse

class PlacesRepository(private val webService: PlacesWebService = PlacesWebService()) {

    suspend fun getPlaces(): PlacesListResponse {
        return webService.getPlaces()
    }

    suspend fun getPlaceDetails(id: String): PlaceDetailsResponse {
        return webService.getPlaceDetails(id)
    }
}