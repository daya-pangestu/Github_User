package com.daya.githubuser.domain.detail

import com.daya.githubuser.data.profile.datasource.ProfileRepository
import com.daya.githubuser.data.profile.general.FollowersFollowing
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetListFollowersUseCase
@Inject
constructor(
    @IoDispatcher
    coroutineDispatcher: CoroutineDispatcher,
    private val repository: ProfileRepository
) : UseCase<String, List<FollowersFollowing>>(coroutineDispatcher) {

    override suspend fun execute(parameters: String): List<FollowersFollowing> {
        return repository.getListFollowersFromNetwork(parameters)
    }

}