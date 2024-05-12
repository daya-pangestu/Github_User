package com.daya.githubuser.presentation.main

import androidx.lifecycle.*
import com.daya.core.data.Resource
import com.daya.core.domain.model.GeneralBio
import com.daya.core.domain.usecase.profile.GetListBioUseCase
import com.daya.core.domain.usecase.profile.GetListFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val getListBioUseCase: GetListBioUseCase,
    private val getListFavoriteUseCase: GetListFavoriteUseCase
) : ViewModel() {

    private val _getListBioUseCase = liveData {
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

}