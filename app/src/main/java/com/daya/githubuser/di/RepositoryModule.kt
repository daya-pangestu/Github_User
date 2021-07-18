package com.daya.githubuser.di

import com.daya.githubuser.data.profile.datasource.ProfileRepository
import com.daya.githubuser.domain.repository.IProfileRepository
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