package com.daya.githubuser.domain.usecase.detail

import com.daya.githubuser.domain.model.GeneralBio
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.repository.IProfileRepository
import com.daya.githubuser.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetDetailBioUseCase
@Inject
constructor(
        private val repository: IProfileRepository,
        @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : UseCase<String, GeneralBio>(coroutineDispatcher) {

    override suspend fun execute(parameters: String): GeneralBio {
        return repository.getDetailBioFromNetwork(parameters)
    }
}