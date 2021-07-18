package com.daya.githubuser.domain.detail

import com.daya.githubuser.data.profile.datasource.ProfileRepository
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class IsUserFavoriteUseCase
@Inject
constructor(
    @IoDispatcher
        coroutineDispatcher: CoroutineDispatcher,
    private val profileRepository: ProfileRepository
) : UseCase<String, Boolean>(coroutineDispatcher) {

    override suspend fun execute(parameters: String): Boolean {
        return profileRepository.observeIsUserFavorite(parameters)
    }

}