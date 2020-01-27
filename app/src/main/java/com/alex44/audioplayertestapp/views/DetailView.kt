package com.alex44.audioplayertestapp.views

import com.alex44.audioplayertestapp.model.enums.PlayerState
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface DetailView : MvpView {

    fun setArtistName(artistName: String)

    fun setTrackName(trackName: String)

    fun setPhoto(photoUrl : String)

    fun showMessage(message: String)

    fun initButtons()

    fun initPlayer(previewUrl: String?)

    @StateStrategyType(value = SkipStrategy::class)
    fun play()

    @StateStrategyType(value = SkipStrategy::class)
    fun pause()

    @StateStrategyType(value = SkipStrategy::class)
    fun stop()

    @StateStrategyType(value = SkipStrategy::class)
    fun updateBarPosition(position: Int)

    @StateStrategyType(value = SkipStrategy::class)
    fun updatePlayerPosition(position: Int)

    @StateStrategyType(value = SkipStrategy::class)
    fun setStartPosition(progress: Int, playerState : PlayerState)

    @StateStrategyType(value = SkipStrategy::class)
    fun updateSecPosition(position: Int)
}