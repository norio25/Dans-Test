package com.norio.danstest.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListJobResponse(
    @Json(name = "")
    val `data`: List<ListJobResponseItem>
)