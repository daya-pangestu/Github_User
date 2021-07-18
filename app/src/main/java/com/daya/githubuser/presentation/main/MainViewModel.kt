package com.daya.githubuser.presentation.main

import androidx.lifecycle.*
import com.daya.githubuser.data.Resource
import com.daya.githubuser.domain.model.GeneralBio
import com.daya.githubuser.domain.usecase.profile.GetListBioUseCase
import com.daya.githubuser.domain.usecase.profile.GetListFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val getListBioUseCase: GetListBioUseCase,
    private val getListFavoriteUseCase: GetListFavoriteUseCase
) : ViewModel() {

    private val _getListBioUseCase = liveData<Resource<List<GeneralBio>>> {
        if (latestValue == null) {
            emit(Resource.Loading)
            emit(getListBioUseCase(Unit))
            return@liveData
        } else {
            emit(latestValue!!)
        }
    }

    val getListBioLiveData
        get() = _getListBioUseCase

    private val _getListFavoriteUseCase = liveData<Resource<List<GeneralBio>>> {
        if (latestValue == null) {
            emit(Resource.Loading)
            emitSource(getListFavoriteUseCase(Unit).asLiveData())
            return@liveData
        } else {
            emit(latestValue!!)
        }
    }

    val getListFavoriteLiveData
        get() = _getListFavoriteUseCase

}