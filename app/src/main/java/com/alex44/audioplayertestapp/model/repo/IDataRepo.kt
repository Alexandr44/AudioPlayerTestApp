package com.alex44.audioplayertestapp.model.repo

import com.alex44.audioplayertestapp.model.dto.DataDTO
import io.reactivex.Maybe

interface IDataRepo {

    fun getData(query : String) : Maybe<List<DataDTO>>

}