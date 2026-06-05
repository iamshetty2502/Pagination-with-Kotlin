package com.shetty.pagination.models


data class ResultData(
    var businesses: ArrayList<Businesses> = arrayListOf(),
    var total: Int? = null,
    var region: Region? = Region()
)