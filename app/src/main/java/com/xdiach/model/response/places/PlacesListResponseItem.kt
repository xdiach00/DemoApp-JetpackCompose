package com.xdiach.model.response.places

import com.xdiach.model.response.Location

data class PlacesListResponseItem(
    val location: Location,
    val name: String,
    val thumbnail_image: String,
    val uuid: String
)