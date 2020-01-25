package com.alex44.audioplayertestapp.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DataDTO (
    @Expose @SerializedName("artistName") val artistName : String? = null,
    @Expose @SerializedName("trackName") val trackName : String? = null,
    @Expose @SerializedName("artworkUrl100") val artworkUrl : String? = null,
    @Expose @SerializedName("previewUrl") val previewUrl : String? = null
) : Serializable