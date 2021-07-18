package com.daya.core.domain.usecase.detail

import com.daya.core.domain.model.FollowersFollowing
import com.daya.core.di.util.IoDispatcher
import com.daya.core.domain.repository.IProfileRepository
import com.daya.core.domain.usecase.UseCase
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