package com.shetty.pagination.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResultDataDto(
    @SerializedName("businesses") var businesses: ArrayList<BusinessesDto> = arrayListOf(),
    @SerializedName("total") var total: Int? = null,
    @SerializedName("region") var region: RegionDto? = RegionDto()
)
