package com.alex44.audioplayertestapp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface HomeView : MvpView {

    fun initRV()

    fun updateRV()

    fun initSearch()

    fun showMessage(message: String)
}