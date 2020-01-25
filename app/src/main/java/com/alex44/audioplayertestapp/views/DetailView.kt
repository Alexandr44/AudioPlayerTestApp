package com.alex44.audioplayertestapp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface DetailView : MvpView {

    fun setArtistName(artistName: String)

    fun setTrackName(trackName: String)

    fun setPhoto(photoUrl : String)

    fun showMessage(message: String)
}