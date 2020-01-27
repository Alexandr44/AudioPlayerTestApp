package com.alex44.audioplayertestapp.presenters

import com.alex44.audioplayertestapp.common.navigation.Screens
import com.alex44.audioplayertestapp.model.dto.DataDTO
import com.alex44.audioplayertestapp.model.enums.PlayerState
import com.alex44.audioplayertestapp.views.DetailView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class DetailPresenter(private val dto: DataDTO?) : MvpPresenter<DetailView>() {

    @Inject
    lateinit var router: Router

    var progress : Int = 0

    var playerState = PlayerState.INIT

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        init()
    }

    private fun init() {
        viewState.setArtistName(dto?.artistName.orEmpty())
        viewState.setTrackName(dto?.trackName.orEmpty())
        viewState.setPhoto(dto?.artworkUrl.orEmpty())
        viewState.initButtons()
        viewState.initPlayer(dto?.previewUrl.orEmpty())
    }

    fun backClicked() : Boolean {
        router.backTo(Screens.HomeScreen())
        return true
    }

    fun playClicked() {
        playerState = PlayerState.PLAY
        viewState.play()
    }

    fun pauseClicked() {
        playerState = PlayerState.PAUSE
        viewState.pause()
    }

    fun stopClicked() {
        playerState = PlayerState.STOP
        viewState.stop()
    }

    fun positionChanged(progress: Int) {
        this.progress = progress
        viewState.updateBarPosition(progress)
        viewState.updateSecPosition(progress)
    }

    fun positionChangedByUser(progress: Int) {
        this.progress = progress
        viewState.updatePlayerPosition(progress)
        viewState.updateSecPosition(progress)
    }

    fun playerPrepared() {
        viewState.setStartPosition(progress, playerState)
    }

}