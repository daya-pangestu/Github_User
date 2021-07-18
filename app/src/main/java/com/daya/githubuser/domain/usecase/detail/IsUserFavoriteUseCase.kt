package com.daya.githubuser.domain.usecase.detail

import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.repository.IProfileRepository
import com.daya.githubuser.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class IsUserFavoriteUseCase
@Inject
constructor(
    @IoDispatcher
        coroutineDispatcher: CoroutineDispatcher,
    private val profileRepository: IProfileRepository
) : UseCase<String, Boolean>(coroutineDispatcher) {

    override suspend fun execute(parameters: String): Boolean {
        return profileRepository.observeIsUserFavorite(parameters)
    }

}