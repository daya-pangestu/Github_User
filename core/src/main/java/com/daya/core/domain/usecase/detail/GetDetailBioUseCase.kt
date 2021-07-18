package com.daya.core.domain.usecase.detail

import com.daya.core.domain.model.GeneralBio
import com.daya.core.di.util.IoDispatcher
import com.daya.core.domain.repository.IProfileRepository
import com.daya.core.domain.usecase.UseCase
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