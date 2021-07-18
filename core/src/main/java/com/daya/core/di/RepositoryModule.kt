package com.daya.core.di

import com.daya.core.data.profile.datasource.ProfileRepository
import com.daya.core.domain.repository.IProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideProfileRepository(profileRepository: ProfileRepository) : IProfileRepository
}