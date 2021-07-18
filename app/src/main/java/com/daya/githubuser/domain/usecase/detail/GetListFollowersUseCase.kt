package com.daya.githubuser.domain.usecase.detail

import com.daya.githubuser.domain.model.FollowersFollowing
import com.daya.githubuser.di.util.IoDispatcher
import com.daya.githubuser.domain.repository.IProfileRepository
import com.daya.githubuser.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetListFollowersUseCase
@Inject
constructor(
    @IoDispatcher
    coroutineDispatcher: CoroutineDispatcher,
    private val repository: IProfileRepository
) : UseCase<String, List<FollowersFollowing>>(coroutineDispatcher) {

    override suspend fun execute(parameters: String): List<FollowersFollowing> {
        return repository.getListFollowersFromNetwork(parameters)
    }

}