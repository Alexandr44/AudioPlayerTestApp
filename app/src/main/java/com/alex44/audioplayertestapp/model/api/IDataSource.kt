package com.alex44.audioplayertestapp.model.api

import com.alex44.audioplayertestapp.model.dto.ResponseDTO
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataSource {

    @GET("search")
    fun getData(@Query("term") query : String) : Maybe<ResponseDTO>

}