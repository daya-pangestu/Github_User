package com.daya.core.domain.usecase.detail

import com.daya.core.di.util.IoDispatcher
import com.daya.core.domain.repository.IProfileRepository
import com.daya.core.domain.usecase.UseCase
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