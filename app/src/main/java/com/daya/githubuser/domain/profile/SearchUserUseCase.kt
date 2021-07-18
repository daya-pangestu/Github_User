package com.daya.githubuser.domain.profile

import com.daya.githubuser.data.profile.datasource.ProfileRepository
import com.daya.githubuser.data.profile.general.GeneralBio
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchUserUseCase
@Inject
constructor(
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        private val repository: ProfileRepository

) : UseCase<String,List<GeneralBio>>(coroutineDispatcher) {

    override suspend fun execute(parameters: String): List<GeneralBio> {
        return repository.searchUsers(parameters)
    }
}