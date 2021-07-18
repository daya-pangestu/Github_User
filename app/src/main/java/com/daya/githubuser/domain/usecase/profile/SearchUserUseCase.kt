package com.daya.githubuser.domain.usecase.profile

import com.daya.githubuser.domain.model.GeneralBio
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.repository.IProfileRepository
import com.daya.githubuser.domain.usecase.UseCase
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