package com.alex44.audioplayertestapp.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseDTO(
    @Expose @SerializedName("resultCount") val resultCount : Int? = null,
    @Expose @SerializedName("results") val results : List<DataDTO>? = null
)