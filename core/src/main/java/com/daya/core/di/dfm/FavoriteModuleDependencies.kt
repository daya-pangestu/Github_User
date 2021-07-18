package com.daya.core.di.dfm

import androidx.lifecycle.ViewModel
import com.daya.core.domain.usecase.profile.GetListFavoriteUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun provideGetListFavoriteUseCase() : GetListFavoriteUseCase
}