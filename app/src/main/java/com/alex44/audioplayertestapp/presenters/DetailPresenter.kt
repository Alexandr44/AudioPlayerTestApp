package com.alex44.audioplayertestapp.presenters

import com.alex44.audioplayertestapp.common.navigation.Screens
import com.alex44.audioplayertestapp.model.dto.DataDTO
import com.alex44.audioplayertestapp.views.DetailView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class DetailPresenter(private val dto: DataDTO?) : MvpPresenter<DetailView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        init()
    }

    private fun init() {
        viewState.setArtistName(dto?.artistName.orEmpty())
        viewState.setTrackName(dto?.trackName.orEmpty())
        viewState.setPhoto(dto?.artworkUrl.orEmpty())
        viewState.initButtons()
    }

    fun backClicked() : Boolean {
        router.backTo(Screens.HomeScreen())
        return true
    }

    fun playClicked() {
        viewState.showMessage("PLAY")
    }

    fun pauseClicked() {
        viewState.showMessage("PAUSE")
    }

    fun stopClicked() {
        viewState.showMessage("STOP")
    }

}