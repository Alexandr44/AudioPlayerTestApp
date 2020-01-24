package com.alex44.audioplayertestapp.model.repo

import com.alex44.audioplayertestapp.common.interfaces.INetworkStatus
import com.alex44.audioplayertestapp.model.api.IDataSource
import com.alex44.audioplayertestapp.model.dto.DataDTO
import io.reactivex.Maybe
import io.reactivex.MaybeEmitter
import io.reactivex.schedulers.Schedulers

class DataRepo(private var source: IDataSource, private val networkStatus: INetworkStatus) : IDataRepo {

    override fun getData(query: String): Maybe<List<DataDTO>> {
        if (networkStatus.isOnline()) {
            return source.getData(query)
                .subscribeOn(Schedulers.io())
                .map { response ->
                    if (response.results != null) {
                        return@map response.results
                    }
                    else return@map ArrayList<DataDTO>()
                }
        }
        else {
            return Maybe.create { emitter: MaybeEmitter<List<DataDTO>> ->
                emitter.onError(RuntimeException("No internet"))
            }
                .subscribeOn(Schedulers.io())
        }
    }

}