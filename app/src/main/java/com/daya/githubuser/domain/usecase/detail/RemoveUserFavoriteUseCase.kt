package com.daya.githubuser.domain.usecase.detail

import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.repository.IProfileRepository
import com.daya.githubuser.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RemoveUserFavoriteUseCase
@Inject
constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repo : IProfileRepository
) : UseCase<String, Unit>(coroutineDispatcher) {

    override suspend fun execute(parameters: String) {
        repo.removeUserFavorite(parameters)
    }

}