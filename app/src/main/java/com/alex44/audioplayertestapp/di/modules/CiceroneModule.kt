package com.alex44.audioplayertestapp.di.modules

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class CiceroneModule {

    @Singleton
    @Provides
    fun cicerone() : Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    fun navigationHolder(cicerone : Cicerone<Router>) : NavigatorHolder = cicerone.navigatorHolder

    @Singleton
    @Provides
    fun router(cicerone: Cicerone<Router>) : Router = cicerone.router

}