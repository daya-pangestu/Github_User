package com.daya.core.domain.usecase.profile

import com.daya.core.data.Resource
import com.daya.core.domain.model.GeneralBio
import com.daya.core.di.util.IoDispatcher
import com.daya.core.domain.repository.IProfileRepository
import com.daya.core.domain.usecase.FlowUseCase
import com.daya.core.utils.toFollowersFollowing
import com.daya.core.utils.toGeneralBio
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetListFavoriteUseCase
@Inject
constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repository: IProfileRepository
) : FlowUseCase<Unit, List<GeneralBio>>(coroutineDispatcher) {

    override fun execute(parameters: Unit): Flow<Resource<List<GeneralBio>>> {
        val listFavEntityWithFollowerFollowing = repository.getAllFavoriteEntity()
        val listGeneralBio = listFavEntityWithFollowerFollowing.map { listBioEntity ->
            val bio = listBioEntity.map {
                it.toGeneralBio().apply {
                    followers = it.followers.toFollowersFollowing()
                    followings = it.following.toFollowersFollowing()
                }
            }
            bio
        }
        return listGeneralBio.map {
            Resource.Success(it)
        }
    }
}