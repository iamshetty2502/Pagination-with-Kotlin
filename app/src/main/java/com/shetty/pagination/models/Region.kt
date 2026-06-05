package com.shetty.pagination.models

import com.google.gson.annotations.SerializedName


data class Region (

  @SerializedName("center" ) var center : Center? = Center()

)