package com.daya.githubuser.domain.detail

import com.daya.githubuser.data.profile.general.GeneralBio
import com.daya.githubuser.data.profile.datasource.ProfileRepository
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetDetailBioUseCase
@Inject
constructor(
        private val repository: ProfileRepository,
        @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : UseCase<String, GeneralBio>(coroutineDispatcher) {

    override suspend fun execute(parameters: String): GeneralBio {
        return repository.getDetailBioFromNetwork(parameters)
    }
}