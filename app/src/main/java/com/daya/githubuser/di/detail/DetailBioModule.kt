@file:Suppress("unused")

package com.daya.githubuser.di.detail

import com.daya.githubuser.data.profile.datasource.DetailBioDataSource
import com.daya.githubuser.data.profile.datasource.NetworkDetailBioDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailBioModule {

    @NetWorkDetailBio
    @Binds
    abstract fun bindLocalProfileDataSource(networkDetailBioDataSource: NetworkDetailBioDataSource): DetailBioDataSource

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class NetWorkDetailBio
