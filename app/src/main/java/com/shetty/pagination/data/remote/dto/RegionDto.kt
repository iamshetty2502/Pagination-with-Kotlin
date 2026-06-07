package com.shetty.pagination.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegionDto(
    @SerializedName("center") var center: CenterDto? = CenterDto()
)
