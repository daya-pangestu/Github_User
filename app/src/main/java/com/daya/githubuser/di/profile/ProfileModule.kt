package com.daya.githubuser.di.profile

import com.daya.githubuser.data.profile.datasource.LocalListBioDataSource
import com.daya.githubuser.data.profile.datasource.NetWorkListBioDataSource
import com.daya.githubuser.data.profile.datasource.ListBioDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {

    @JsonProfile
    @Binds
    abstract fun bindLocalProfileDataSource(localProfileDataSource: LocalListBioDataSource): ListBioDataSource

    @NetWorkProfile
    @Binds
    abstract fun bindNetWorkProfileDataSource(netWorkProfileDataSource: NetWorkListBioDataSource): ListBioDataSource

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class JsonProfile

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class NetWorkProfile