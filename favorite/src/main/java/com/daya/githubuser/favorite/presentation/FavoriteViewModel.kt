package com.daya.githubuser.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.daya.core.data.Resource
import com.daya.core.domain.model.GeneralBio
import com.daya.core.domain.usecase.profile.GetListFavoriteUseCase
import javax.inject.Inject

class FavoriteViewModel
@Inject
constructor(
    private val getListFavoriteUseCase: GetListFavoriteUseCase
) : ViewModel() {

    private val _getListFavoriteLiveData = liveData {
            emit(Resource.Loading)
            emitSource(getListFavoriteUseCase(Unit).asLiveData())
    }

    val getListFavoriteLiveData = _getListFavoriteLiveData
}