package com.daya.githubuser.favorite.di

import android.content.Context
import com.daya.core.di.dfm.FavoriteModuleDependencies
import com.daya.githubuser.favorite.presentation.FavoriteActivity
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {
    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder{
        fun context(@BindsInstance context: Context) : Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build() : FavoriteComponent
    }
}