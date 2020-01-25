package com.alex44.audioplayertestapp.di.modules

import com.alex44.audioplayertestapp.common.interfaces.INetworkStatus
import com.alex44.audioplayertestapp.model.api.IDataSource
import com.alex44.audioplayertestapp.model.repo.DataRepo
import com.alex44.audioplayertestapp.model.repo.IDataRepo
import dagger.Module
import dagger.Provides

@Module(includes = [ApiModule::class, NetworkModule::class])
class RepoModule {

    @Provides
    fun repo(dataSource : IDataSource, networkStatus : INetworkStatus) : IDataRepo = DataRepo(dataSource, networkStatus)

}