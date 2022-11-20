package com.xdiach.model.response.place

import com.xdiach.model.response.Location

data class PlaceDetailsResponse(
    val address: Address,
    val description: String,
    val image: String,
    val location: Location,
    val name: String,
    val thumbnail_image: String,
    val url: String,
    val uuid: String
)