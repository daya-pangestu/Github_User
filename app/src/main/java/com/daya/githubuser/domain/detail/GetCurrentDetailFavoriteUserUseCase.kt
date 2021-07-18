package com.daya.githubuser.domain.detail

import com.daya.githubuser.data.profile.datasource.ProfileRepository
import com.daya.githubuser.data.profile.general.GeneralBio
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetCurrentDetailFavoriteUserUseCase
@Inject
constructor(
    @IoDispatcher
        coroutineDispatcher: CoroutineDispatcher,
    private val repo : ProfileRepository
) : UseCase<String, GeneralBio>(coroutineDispatcher) {

    override suspend fun execute(parameters: String): GeneralBio {
        return repo.getCurrentUser(parameters)!! // throw should be wrapped
    }
}