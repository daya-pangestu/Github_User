package com.daya.githubuser.favorite.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daya.core.domain.usecase.profile.GetListFavoriteUseCase
import com.daya.githubuser.favorite.presentation.FavoriteViewModel
import javax.inject.Inject

class ViewModelFactory
@Inject
constructor(
    private val getListFavoriteUseCase: GetListFavoriteUseCase
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(getListFavoriteUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}