package com.daya.core.domain.usecase.profile

import com.daya.core.domain.model.GeneralBio
import com.daya.core.di.util.IoDispatcher
import com.daya.core.domain.repository.IProfileRepository
import com.daya.core.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchUserUseCase
@Inject
constructor(
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        private val repository: IProfileRepository

) : UseCase<String, List<GeneralBio>>(coroutineDispatcher) {

    override suspend fun execute(parameters: String): List<GeneralBio> {
        return repository.searchUsers(parameters)
    }
}