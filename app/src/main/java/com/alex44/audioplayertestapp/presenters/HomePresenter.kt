package com.alex44.audioplayertestapp.presenters

import com.alex44.audioplayertestapp.common.navigation.Screens
import com.alex44.audioplayertestapp.model.dto.DataDTO
import com.alex44.audioplayertestapp.model.repo.IDataRepo
import com.alex44.audioplayertestapp.views.HomeRVItemView
import com.alex44.audioplayertestapp.views.HomeView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class HomePresenter(private val mainThreadScheduler : Scheduler) : MvpPresenter<HomeView>() {

    @Inject
    lateinit var repo : IDataRepo

    @Inject
    lateinit var router: Router

    var disposable : Disposable? = null

    var data = ArrayList<DataDTO>()

    var rvPosition = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        init()
    }

    private fun init() {
        viewState.initSearch()
        viewState.initRV()
    }

    private fun loadDataFromApi(text: String) {
        data.clear()
        disposable = repo.getData(text)
            .observeOn(mainThreadScheduler)
            .subscribe({ list ->
                data.addAll(list)
                viewState.updateRV()
            }, { t ->
                t?.message?.let {
                    viewState.showMessage(it)
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    fun bind(dataHolder: HomeRVItemView) {
        val dto = data[dataHolder.getItemPosition()]
        dataHolder.setArtistName(dto.artistName.orEmpty())
        dataHolder.setTrackName(dto.trackName.orEmpty())
        dataHolder.setPhoto(dto.artworkUrl.orEmpty())
    }

    fun clicked(position: Int) {
        val dto = data[position]
        router.navigateTo(Screens.DetailScreen(dto))
    }

    fun searchTextEntered(newText: String) {
        disposable?.let {
            if (!it.isDisposed) it.dispose()
        }
        loadDataFromApi(newText)
    }

}