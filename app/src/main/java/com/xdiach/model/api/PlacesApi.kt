package com.xdiach.model.api

import com.xdiach.model.response.place.PlaceDetailsResponse
import com.xdiach.model.response.places.PlacesListResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class PlacesWebService {
    private lateinit var api: PlacesApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://stub.bbeight.synetech.cz/demo/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(PlacesApi::class.java)
    }

    suspend fun getPlaces(): PlacesListResponse {
        return api.getPlaces()
    }

    suspend fun getPlaceDetails(id: String): PlaceDetailsResponse {
        return api.getPlaceDetails(id)
    }

    interface PlacesApi {
        @GET("places")
        suspend fun getPlaces(): PlacesListResponse

        @GET("places/{uuid}")
        suspend fun getPlaceDetails(@Path("uuid") uuid: String): PlaceDetailsResponse
    }
}