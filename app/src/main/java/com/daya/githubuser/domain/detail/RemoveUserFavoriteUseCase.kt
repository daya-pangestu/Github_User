package com.daya.githubuser.domain.detail

import com.daya.githubuser.data.profile.datasource.ProfileRepository
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RemoveUserFavoriteUseCase
@Inject
constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repo : ProfileRepository
) : UseCase<String,Unit>(coroutineDispatcher) {

    override suspend fun execute(parameters: String) {
        repo.removeUserFavorite(parameters)
    }

}