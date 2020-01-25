package com.alex44.audioplayertestapp.di

import com.alex44.audioplayertestapp.di.modules.AppModule
import com.alex44.audioplayertestapp.di.modules.CiceroneModule
import com.alex44.audioplayertestapp.di.modules.ImageModule
import com.alex44.audioplayertestapp.di.modules.RepoModule
import com.alex44.audioplayertestapp.presenters.DetailPresenter
import com.alex44.audioplayertestapp.presenters.HomePresenter
import com.alex44.audioplayertestapp.presenters.MainPresenter
import com.alex44.audioplayertestapp.ui.activities.MainActivity
import com.alex44.audioplayertestapp.ui.adapters.HomeRVAdapter
import com.alex44.audioplayertestapp.ui.fragments.DetailFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ImageModule::class, CiceroneModule::class, RepoModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainActivity: MainPresenter)
    fun inject(presenter: HomePresenter)
    fun inject(adapter: HomeRVAdapter)
    fun inject(detailFragment: DetailFragment)
    fun inject(presenter: DetailPresenter)

}